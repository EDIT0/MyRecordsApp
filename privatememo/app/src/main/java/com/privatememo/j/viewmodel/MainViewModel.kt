package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.datamodel.CategoryInfo2
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
    var password = String()

    var items = ObservableArrayList<CategoryInfo2>()

    init {
        totalCateNum.value = 0
        totalConNum.value = 0

        Log.i("tag","생성자 호출 ${email.getValue()}")
    }

    fun search(){
        items.clear()
        getCategoryList_call()
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

    fun getCategoryList_call(){

        val call: Call<CategoryInfo> = retrofit2module.BaseModule().getCategoryList(email.value!!)

        call.enqueue(object : Callback<CategoryInfo> {
            override fun onResponse(call: Call<CategoryInfo>, response: Response<CategoryInfo>) {
                val result: CategoryInfo? = response.body()
                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                }
                Log.i("tag","메모장 카테고리 리스트 수: ${items.size}")
            }
            override fun onFailure(call: Call<CategoryInfo>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }
}