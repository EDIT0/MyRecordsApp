package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel : ViewModel() {

    var retrofit2module = Retrofit2Module()

    var items = ObservableArrayList<CategoryInfo2>()
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
        getCategoryList_call()
    }

    fun getCategoryList_call(){

        val call: Call<CategoryInfo> = retrofit2module.client.getCategoryList(email.get().toString())

        call.enqueue(object : Callback<CategoryInfo> {
            override fun onResponse(call: Call<CategoryInfo>, response: Response<CategoryInfo>) {
                val result: CategoryInfo? = response.body()

                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                }

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")
                switching()
            }

            override fun onFailure(call: Call<CategoryInfo>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }

}