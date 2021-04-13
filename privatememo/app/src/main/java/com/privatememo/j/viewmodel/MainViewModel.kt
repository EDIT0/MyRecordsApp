package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.MemoCountInfo
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    val retrofit2module = Retrofit2Module.getInstance()

    var email = MutableLiveData<String>()
    var nickname = MutableLiveData<String>()
    var motto = MutableLiveData<String>()
    var picPath = MutableLiveData<String>()
    var totalCateNum = MutableLiveData<Int>()
    var totalConNum = MutableLiveData<Int>()

    init {
        totalCateNum.value = 0
        totalConNum.value = 0

        Log.i("tag","생성자 호출 ${email.getValue()}")
    }


    fun getMemoCount_call(){
        val call: Call<MemoCountInfo> = retrofit2module.BaseModule().getMemoCount(email.value?:"")

        call.enqueue(object : Callback<MemoCountInfo> {
            override fun onResponse(call: Call<MemoCountInfo>, response: Response<MemoCountInfo>) {
                val result: MemoCountInfo? = response.body()
                totalConNum.value = Integer.parseInt(result?.MemoCount)
                Log.i("??","겟메모카운트ok ${totalConNum.value}")
            }

            override fun onFailure(call: Call<MemoCountInfo>, t: Throwable) {
                Log.i("??","겟메모카운트error")
                totalConNum.value = 0
            }
        })
    }
}