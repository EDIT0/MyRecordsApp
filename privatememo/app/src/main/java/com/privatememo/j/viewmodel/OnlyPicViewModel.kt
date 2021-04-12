package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.datamodel.OnlyPicInfo
import com.privatememo.j.datamodel.OnlyPicInfo2
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlyPicViewModel : ViewModel(){

    var retrofit2module = Retrofit2Module()

    var items = ObservableArrayList<OnlyPicInfo2>()
    var email = ObservableField<String>()
    var controler = MutableLiveData<Boolean>()

    init {
        controler.value = false
    }

    fun switching(){
        if(items.size == 0){
            controler.value = false
        }
        else{
            controler.value = true
        }
    }

    fun search(){
        items.clear()
        getOnlyPic_call()
    }


    fun getOnlyPic_call(){

        val call: Call<OnlyPicInfo> = retrofit2module.client.getOnlyPic(email.get().toString())

        call.enqueue(object : Callback<OnlyPicInfo> {
            override fun onResponse(call: Call<OnlyPicInfo>, response: Response<OnlyPicInfo>) {
                val result: OnlyPicInfo? = response.body()

                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                }

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")
                switching()
            }

            override fun onFailure(call: Call<OnlyPicInfo>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }


    fun deleteMemo_call(contentNum: Int){
        val call: Call<String> = retrofit2module.client.DeleteMemo(contentNum)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result: String? = response.body()

                Log.i("tag","이거 출력됩니까?")

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","이거 출력 안됩니까?")
            }
        })
    }



}