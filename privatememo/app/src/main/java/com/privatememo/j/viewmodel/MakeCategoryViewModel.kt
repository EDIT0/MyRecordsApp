package com.privatememo.j.viewmodel

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MakeCategoryViewModel : ViewModel() {

    var retrofit2module = Retrofit2Module()

    var email = ObservableField<String>()
    var cateName = ObservableField<String>()
    var cateExplanation = ObservableField<String>()
    var pictureUri = ObservableField<Uri>()
    var pictureAddress = ObservableField<String>()

    var sendImageToServer = MutableLiveData<String>()


    init {
        pictureAddress.set("")
    }



    var categoryComment = ObservableField<String>()
    fun completeButton(){
        var imageAddress = "http://edit0@edit0.dothome.co.kr/MyRecords/OnlyImage/Category/"

        if(cateName.get() != null && cateExplanation.get() != null){

            if(pictureUri.get() != null){
                sendImageToServer.value = "image_yes"
            }
            else{
                sendImageToServer.value = "image_no"
            }

            Log.i("tag","이거 뭐 나옴?  ${email.get().toString()}")

            CategoryInsert_call(email.get().toString(), cateName.get().toString(),
                cateExplanation.get().toString(), imageAddress+pictureAddress.get().toString())
            //http://edit0@edit0.dothome.co.kr/MyRecords/OnlyImage/akdmadl34%40naver.com.png

            Log.i("TAG","보내자")
        }
        else{
            categoryComment.set("프로필을 완성해주세요.")
            Log.i("TAG","못보냄")
        }

    }


    fun CategoryInsert_call(vararg str: String){
        val call: Call<String> = retrofit2module.client.CategoryInsert(str[0], str[1], str[2], str[3])

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result: String? = response.body()
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }



}