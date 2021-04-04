package com.privatememo.j.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.privatememo.j.datamodel.MemberInfo
import com.privatememo.j.datamodel.ReturnCheck
import com.privatememo.j.utility.Retrofit2Module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeViewModel : ViewModel() {

    var retrofit2module = Retrofit2Module()

    var getEmailfromMember = ObservableField<String>()
    var getPasswordfromMember = ObservableField<String>()
    var emailfromServer = ObservableField<String>()
    var nicknamefromServer = ObservableField<String>()
    var mottofromServer = ObservableField<String>()
    var picPathfromServer = ObservableField<String>()
    var login_check = ObservableField<String>()

    var communication_check =  MutableLiveData<Boolean>()


    //나중에 초기화 지워줘야 함
    init {
        getEmailfromMember.set("akdmadl34@naver.com")
        getPasswordfromMember.set("123")
    }


    fun LoginButton(){
        Login_call(getEmailfromMember.get().toString(), getPasswordfromMember.get().toString())
    }



    fun Login_call(email: String, password: String){
        val call: Call<MemberInfo> = retrofit2module.client.Login(email, password)

        call.enqueue(object : Callback<MemberInfo> {
            override fun onResponse(call: Call<MemberInfo>, response: Response<MemberInfo>) {
                val result: MemberInfo? = response.body()
                login_check.set(result?.returnvalue)
                emailfromServer.set(result?.email)
                nicknamefromServer.set(result?.nickname)
                mottofromServer.set(result?.motto)
                picPathfromServer.set(result?.picPath)

                communication_check.setValue(true)

                Log.i("tag","통신으로 받은 데이터 ${result?.email}")
                Log.i("tag","통신으로 받은 데이터 ${emailfromServer.get().toString()}")

            }

            override fun onFailure(call: Call<MemberInfo>, t: Throwable) {
                Log.i("??","error")
            }
        })
    }

}