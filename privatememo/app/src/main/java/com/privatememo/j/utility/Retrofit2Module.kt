package com.privatememo.j.utility

import android.util.Log
import com.privatememo.j.api.Retrofit2API
import com.privatememo.j.datamodel.ReturnCheck
import com.privatememo.j.viewmodel.SignUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit2Module {

    var baseurl = "http://edit0.dothome.co.kr/"
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseurl)
        .build()
    val client: Retrofit2API = retrofit.create(Retrofit2API::class.java)

}