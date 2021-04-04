package com.privatememo.j.ui.bottombar.memo

import android.app.Activity
import android.content.ContentProvider
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.privatememo.j.R
import com.privatememo.j.api.Retrofit2API
import com.privatememo.j.databinding.ActivityMainBinding
import com.privatememo.j.databinding.MakecategoryBinding
import com.privatememo.j.viewmodel.MainViewModel
import com.privatememo.j.viewmodel.MakeCategoryViewModel
import kotlinx.android.synthetic.main.makecategory.*
import kotlinx.android.synthetic.main.makecategory.backbutton
import kotlinx.android.synthetic.main.makecategory.picture
import kotlinx.android.synthetic.main.signupactivity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MakeCategory : AppCompatActivity() {

    lateinit var MakeCategoryBinding: MakecategoryBinding
    var makeCategoryViewModel = MakeCategoryViewModel()

    lateinit var UriResult: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        makeCategoryViewModel = ViewModelProvider(this).get(MakeCategoryViewModel::class.java)
        MakeCategoryBinding = DataBindingUtil.setContentView(this, R.layout.makecategory)
        MakeCategoryBinding.setLifecycleOwner(this)
        MakeCategoryBinding.makeCategoryViewModel = makeCategoryViewModel

        var getintent = getIntent()
        makeCategoryViewModel.email.set(getintent.getStringExtra("email"))

        backbutton.setOnClickListener {
            finish()
        }

        MakeCategoryBinding.picture.setOnClickListener {
            val intent = Intent("com.android.camera.action.CROP")
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "선택"), 101)
        }

        var sendImageToServer = Observer<String> { result ->
            if(result == "image_yes") {
                send_images_testRetrofit()
            }
            else if(result == "image_no") {

            }

            finish()
        }
        makeCategoryViewModel?.sendImageToServer?.observe(MakeCategoryBinding.lifecycleOwner!!, sendImageToServer)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            UriResult = data.getData()!!

            if (resultCode == Activity.RESULT_OK) {
                Glide.with(this).load(UriResult).override(1000,1000).into(picture)
                makeCategoryViewModel.pictureUri.set(UriResult)

            } else if (requestCode == 101 && resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();
            }
        }
    }


    fun send_images_testRetrofit(){
        var date = SimpleDateFormat()
        var dt = Date()
        date = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        println(date.format(dt).toString())
        //var date_filename = date.format(dt).toString()
        Log.i("TAG","아아아아아"+makeCategoryViewModel.pictureUri.get()!!)
        var path = absolutelyPath(makeCategoryViewModel.pictureUri.get()!!)
        println("경로 받아라 ! "+path)
        val file = File(path)
        var fileName = file.getName()
        fileName = "Category_${makeCategoryViewModel.email.get().toString()}_${date.format(dt).toString()}_.png"
        Log.i("tag","마 ! 이게 사진 제목이다. ${fileName}")
        makeCategoryViewModel.pictureAddress.set(fileName)


        var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/jpg"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)

        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://edit0.dothome.co.kr/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        var server = retrofit.create(Retrofit2API::class.java)

        server.CategoryImageSender("name2.png",body).enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("레트로핏 결과1","에러")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response?.isSuccessful) {
                    //Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
                    Log.d("레트로핏 결과2",""+response?.body().toString())

                } else {
                    //Toast.makeText(getApplicationContext(), "Some error occurred...", Toast.LENGTH_LONG).show();
                }
            }
        })
    }

    fun absolutelyPath(path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }
}