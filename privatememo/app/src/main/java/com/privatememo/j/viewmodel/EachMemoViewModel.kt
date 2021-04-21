package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.MemoInfo
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.utility.Retrofit2Module
import com.privatememo.j.utility.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EachMemoViewModel : ViewModel() {

    val retrofit2module = Retrofit2Module.getInstance()

    var cateName = String()
    var email = String()
    var cateNum = String()

    var items = ObservableArrayList<MemoInfo2>()
    var controler = MutableLiveData<Boolean>()
    var sortToggle = MutableLiveData<Boolean>()

    init {
        controler.value = false
        sortToggle.value = false
    }

    fun sortButton(){
        if(sortToggle.value == false){
            sortToggle.value = true
        }
        else if(sortToggle.value == true){
            sortToggle.value = false
        }
    }

    fun itemsEmpty(){
        items.clear()
    }


    fun switching(){
        if(items.size == 0){
            controler.value = false
        }
        else{
            controler.value = true
        }
    }

    fun search(Min:Int, Max: Int, SortState: Int){
        items.clear()
        getMemoList_call(Min, Max, SortState)
    }

    fun whenScrolled(Mid:Int, Max: Int, SortState: Int){
        getMemoList_call(Mid, Max, SortState)
    }

    fun getMemoList_call(start:Int, end: Int, SortState: Int){

        val call: Call<MemoInfo> = retrofit2module.BaseModule().getMemoList(Integer.parseInt(cateNum), start, end, SortState)

        call.enqueue(object : Callback<MemoInfo> {
            override fun onResponse(call: Call<MemoInfo>, response: Response<MemoInfo>) {
                val result: MemoInfo? = response.body()

                if(Utility.EachMemoFloating.FloatingState == 1){
                    Collections.reverse(items)
                }

                for (i in 0 until result?.result?.size!!) {

                    items.add(result.result.get(i))
                }

                if(Utility.EachMemoFloating.FloatingState == 1){
                    Collections.reverse(items)
                }

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
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("??","이거 출력 안됩니까?")
            }
        })
    }

}