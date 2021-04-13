package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.datamodel.MemoInfo
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EachMemoViewModel : ViewModel() {

    val retrofit2module = Retrofit2Module.getInstance()

    var cateName = String()
    var email = String()
    var cateNum = String()

    var items = ObservableArrayList<MemoInfo2>()
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
        getMemoList_call()
    }

    fun getMemoList_call(){

        val call: Call<MemoInfo> = retrofit2module.BaseModule().getMemoList(Integer.parseInt(cateNum))

        call.enqueue(object : Callback<MemoInfo> {
            override fun onResponse(call: Call<MemoInfo>, response: Response<MemoInfo>) {
                val result: MemoInfo? = response.body()

                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                }

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")
                switching()
            }

            override fun onFailure(call: Call<MemoInfo>, t: Throwable) {
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

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","이거 출력 안됩니까?")
            }
        })
    }

}