package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){

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
}