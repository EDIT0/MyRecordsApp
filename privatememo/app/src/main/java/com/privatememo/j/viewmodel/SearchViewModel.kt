package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.*
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val retrofit2module = Retrofit2Module.getInstance()

    var items = ObservableArrayList<SearchInfo2>()
    var email = ObservableField<String>()
    var controler = MutableLiveData<Boolean>()
    var keyword = String()


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


    fun getSearchResult_call(){

        val call: Call<SearchInfo> = retrofit2module.BaseModule().getSearchResult(email.get().toString(), keyword)

        Log.i("tag","getSearchResult 호출 ${email.get().toString()}  ${keyword}")
        call.enqueue(object : Callback<SearchInfo> {
            override fun onResponse(call: Call<SearchInfo>, response: Response<SearchInfo>) {
                val result: SearchInfo? = response.body()

                for (i in 0 until result?.result?.size!!) {
                    items.add(result.result.get(i))
                    Log.i("tag","검색 결과 값: ${result.result.get(i).title}")
                }

                //Log.i("tag","설명 입니다. ${result?.result?.get(0)?.explanation}")
                switching()
            }

            override fun onFailure(call: Call<SearchInfo>, t: Throwable) {
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