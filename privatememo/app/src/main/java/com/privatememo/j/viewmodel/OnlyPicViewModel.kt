package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.OnlyPicInfo
import com.privatememo.j.utility.Retrofit2Module
import com.privatememo.j.utility.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnlyPicViewModel : ViewModel(){

    val retrofit2module = Retrofit2Module.getInstance()

    var items = ObservableArrayList<OnlyPicInfo.OnlyPicInfo2>()
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

    fun search(Min: Int, Max: Int){
        items.clear()
        getOnlyPic_call(Min, Max)
    }

    fun whenScrolled(Mid: Int, Max: Int){
        getOnlyPic_call(Mid, Max)
    }


    fun getOnlyPic_call(start: Int, end: Int){

        val call: Call<OnlyPicInfo> = retrofit2module.BaseModule().getOnlyPic(email.get().toString(), start, end)

        call.enqueue(object : Callback<OnlyPicInfo> {
            override fun onResponse(call: Call<OnlyPicInfo>, response: Response<OnlyPicInfo>) {
                val result: OnlyPicInfo? = response.body()

                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                }

                Utility.OnlyPicLoadMore.OnlyPicMax = items.size
                Utility.OnlyPicLoadMore.OnlyPicMid = Utility.OnlyPicLoadMore.OnlyPicMax - 6

                switching()
            }

            override fun onFailure(call: Call<OnlyPicInfo>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }


    fun deleteMemo_call(contentNum: Int){
        val call: Call<String> = retrofit2module.BaseModule().DeleteMemo(contentNum)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result: String? = response.body()
                Log.i("tag","이거 출력됩니까?")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","이거 출력 안됩니까?")
            }
        })
    }



}