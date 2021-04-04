package com.privatememo.j.api

import com.privatememo.j.datamodel.CategoryInfo
import com.privatememo.j.datamodel.MemberInfo
import com.privatememo.j.datamodel.ReturnCheck
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface Retrofit2API {

    @FormUrlEncoded
    @POST("MyRecords/SignUp/SignUpEmailSender.php")
    fun EmailSender(
        @Field("email") email: String,
        @Field("num") num: String
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/SignUp/SignUpProfileInsert.php")
    fun ProfileInsert(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("nickname") nickname: String,
        @Field("motto") motto: String,
        @Field("picPath") picPath: String
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/SignUp/SignUpEmailCheck.php")
    fun ProfileEmailCheck(
        @Field("email") email: String
    ): Call<ReturnCheck>

    @Multipart
    @POST("MyRecords/SignUp/SignUpProfileImageSender.php")
    fun ProfileImageSender(
        @Part("userId") userId: String,
        @Part imageFile : MultipartBody.Part
    ): Call<String>

    @Multipart
    @POST("MyRecords/Memo/CategoryImageSender.php")
    fun CategoryImageSender(
        @Part("userId") userId: String,
        @Part imageFile : MultipartBody.Part
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/Login/Login.php")
    fun Login(
            @Field("email") email: String,
            @Field("password") password: String
    ): Call<MemberInfo>

    @FormUrlEncoded
    @POST("MyRecords/Memo/CategoryInsert.php")
    fun CategoryInsert(
        @Field("email") email: String,
        @Field("catename") catename: String,
        @Field("explanation") explanation: String,
        @Field("catepicPath") catepicPath: String
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/Memo/getCategoryList.php")
    fun getCategoryList(
        @Field("email") email: String
    ): Call<CategoryInfo>


}