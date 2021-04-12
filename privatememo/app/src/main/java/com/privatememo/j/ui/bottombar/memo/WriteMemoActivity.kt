package com.privatememo.j.ui.bottombar.memo

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.privatememo.j.R
import com.privatememo.j.api.Retrofit2API
import com.privatememo.j.databinding.WritememoactivityBinding
import com.privatememo.j.utility.Retrofit2Module
import com.privatememo.j.viewmodel.WriteMemoViewModel
import kotlinx.android.synthetic.main.memofragment.*
import kotlinx.android.synthetic.main.memofragment.backbutton
import kotlinx.android.synthetic.main.signupactivity.*
import kotlinx.android.synthetic.main.writememoactivity.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.random.Random

class WriteMemoActivity : AppCompatActivity() {

    lateinit var WriteMemoBinding: WritememoactivityBinding
    var writeMemoViewModel = WriteMemoViewModel()

    lateinit var one:ImageView
    lateinit var two:ImageView
    lateinit var three:ImageView
    lateinit var four:ImageView
    lateinit var five:ImageView
    lateinit var showImageView:LinearLayout
    var ViewArray = ArrayList<View>()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        writeMemoViewModel = ViewModelProvider(this).get(WriteMemoViewModel::class.java)
        WriteMemoBinding = DataBindingUtil.setContentView(this, R.layout.writememoactivity)
        WriteMemoBinding.setLifecycleOwner(this)
        WriteMemoBinding.writeMemoViewModel = writeMemoViewModel

        showImageView = findViewById(R.id.imagelayout)
        one = ImageView(this)
        two = ImageView(this)
        three = ImageView(this)
        four = ImageView(this)
        five = ImageView(this)
        one.scaleType = ImageView.ScaleType.FIT_XY
        two.scaleType = ImageView.ScaleType.FIT_XY
        three.scaleType = ImageView.ScaleType.FIT_XY
        four.scaleType = ImageView.ScaleType.FIT_XY
        five.scaleType = ImageView.ScaleType.FIT_XY
        one.setBackgroundColor(Color.WHITE)
        two.setBackgroundColor(Color.WHITE)
        three.setBackgroundColor(Color.WHITE)
        four.setBackgroundColor(Color.WHITE)
        five.setBackgroundColor(Color.WHITE)
        var layoutParams = LinearLayout.LayoutParams(300, 300)
        one.layoutParams = layoutParams
        two.layoutParams = layoutParams
        three.layoutParams = layoutParams
        four.layoutParams = layoutParams
        five.layoutParams = layoutParams

        backbutton.setOnClickListener {
            finish()
        }

        var getintent = getIntent()
        writeMemoViewModel.email = getintent.getStringExtra("email")!!
        writeMemoViewModel.cateNum = getintent.getStringExtra("catenum")!!

        addImage.setOnClickListener {
            val intent = Intent("com.android.camera.action.CROP")
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(Intent.createChooser(intent, "선택"), 111)
        }


        one.setOnClickListener {
            ViewArray.remove(one)
            writeMemoViewModel.uriHash.remove("one")
            RecallImage()
        }

        two.setOnClickListener {
            ViewArray.remove(two)
            //uri_array.remove(uri_array_name[1])
            writeMemoViewModel.uriHash.remove("two")
            RecallImage()
        }

        three.setOnClickListener {
            ViewArray.remove(three)
            //uri_array.remove(uri_array_name[2])
            writeMemoViewModel.uriHash.remove("three")
            RecallImage()
        }

        four.setOnClickListener {
            ViewArray.remove(four)
            //uri_array.remove(uri_array_name[3])
            writeMemoViewModel.uriHash.remove("four")
            RecallImage()
        }

        five.setOnClickListener {
            ViewArray.remove(five)
            //uri_array.remove(uri_array_name[4])
            writeMemoViewModel.uriHash.remove("five")
            RecallImage()
        }

        var sendImageToServer = Observer<String> { result ->
            if(result == "image_yes") {
                for(i in writeMemoViewModel.uriHash) {
                    send_images_Retrofit(i.value)
                    Log.i("tag","키 벨류 : "+i.value +" / "+ i.key)
                }
            }
            else if(result == "image_no") {

            }
            setResult(RESULT_OK)
            finish()
        }
        writeMemoViewModel?.sendImageToServer?.observe(WriteMemoBinding.lifecycleOwner!!, sendImageToServer)

    }

    fun RecallImage(){
        showImageView.removeAllViews()
        for(i in 0 until ViewArray.size){
            showImageView.addView(ViewArray.get(i))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 111 && resultCode == RESULT_OK && data != null) {
            //Toast.makeText(applicationContext, "" + data.clipData?.itemCount,Toast.LENGTH_SHORT).show()

            /*if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                result = CropImage.getActivityResult(data)*/
            var result = data.getData()

            if(resultCode == Activity.RESULT_OK){
                if(!ViewArray.contains(one)){
                    writeMemoViewModel.uriHash.put("one",result!!)
                    ViewArray.add(one)
                    Glide.with(this).load(result).override(1000,1000).into(one)
                    showImageView.addView(one)
                    println("1 파일이름을 찾아라! / ${result}")
                }
                else if(!ViewArray.contains(two)){
                    writeMemoViewModel.uriHash.put("two",result!!)
                    ViewArray.add(two)
                    Glide.with(this).load(result).override(1000,1000).into(two)
                    showImageView.addView(two)
                    println("2 / ${result}")
                }
                else if(!ViewArray.contains(three)){
                    writeMemoViewModel.uriHash.put("three",result!!)
                    ViewArray.add(three)
                    Glide.with(this).load(result).override(1000,1000).into(three)
                    showImageView.addView(three)
                    println("3 / ${result}")
                }
                else if(!ViewArray.contains(four)){
                    writeMemoViewModel.uriHash.put("four",result!!)
                    ViewArray.add(four)
                    Glide.with(this).load(result).override(1000,1000).into(four)
                    showImageView.addView(four)
                    println("4 / ${result}")
                }
                else if(!ViewArray.contains(five)){
                    writeMemoViewModel.uriHash.put("five",result!!)
                    ViewArray.add(five)
                    Glide.with(this).load(result).override(1000,1000).into(five)
                    showImageView.addView(five)
                    println("5 / ${result}")
                }
            }
        }
        else if(requestCode == 111 && resultCode == RESULT_CANCELED){
            //Toast.makeText(this,"취소", Toast.LENGTH_SHORT).show();
        }

    }

    fun absolutelyPath(path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        var result = c?.getString(index!!)

        return result!!
    }

    fun send_images_Retrofit(uri: Uri){

        count++

        var path = absolutelyPath(uri)
        println("경로 받아라 ! "+path)

        val file = File(path)
        var fileName = file.getName()

        var random = Random
        var randomNum = 0
        randomNum = random.nextInt(100000, 999999)

        fileName = "${writeMemoViewModel.email}_${randomNum}_"+fileName+"_${count}.png"

        var requestBody : RequestBody = RequestBody.create(MediaType.parse("image/jpg"),file)
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)

        //DB에 주소 넣기
        InsertImageAddressToServer(fileName)


        //The gson builder
        var gson : Gson =  GsonBuilder()
            .setLenient()
            .create()

        //creating retrofit object
        var retrofit =
            Retrofit.Builder()
                .baseUrl("http://edit0.dothome.co.kr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        //creating our api

        var server = retrofit.create(Retrofit2API::class.java)

        // 파일, 사용자 아이디, 파일이름
        server.MemoImageSender("name2.png",body).enqueue(object: Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("레트로핏 결과1","에러")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response?.isSuccessful) {
                    Log.d("레트로핏 결과2",""+response?.body().toString())
                    //image.setImageURI(uri)
                } else {
                }
            }
        })
    }

    fun InsertImageAddressToServer(fileName: String){
        var retrofit2module = Retrofit2Module()

        var imageAddress = "http://edit0@edit0.dothome.co.kr/MyRecords/OnlyImage/Memo/"
        val call: Call<String> = retrofit2module.client.InsertImageAddressToServer(imageAddress+fileName, writeMemoViewModel.email, Integer.parseInt(writeMemoViewModel.contentnum))

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result: String? = response.body()
                Log.i("tag","디비로 이미지 전송 완료")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","error!!")
            }
        })
    }


}