package com.privatememo.j.api

import com.privatememo.j.datamodel.*
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

    @FormUrlEncoded
    @POST("MyRecords/Memo/MemoInsert.php")
    fun MemoInsert(
        @Field("title") title: String,
        @Field("memo") memo: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("memberlist_email") email: String,
        @Field("category_catenum") catenum: Int
    ): Call<String>

    // 프로필 이미지 보내기
    @Multipart
    @POST("MyRecords/Memo/MemoImageSender.php")
    fun MemoImageSender(
        @Part("userId") userId: String,
        @Part imageFile : MultipartBody.Part): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/Memo/InsertImageAddressToServer.php")
    fun InsertImageAddressToServer(
        @Field("imagePath") imagePath: String,
        @Field("memberlist_email") memberlist_email: String,
        @Field("content_contentnum") content_contentnum: Int
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/Memo/getMemoList.php")
    fun getMemoList(
        @Field("category_catenum") catenum: Int
    ): Call<MemoInfo>

    @FormUrlEncoded
    @POST("MyRecords/Memo/DeleteMemo.php")
    fun DeleteMemo(
            @Field("contentNum") ContentNum: Int
    ): Call<String>

    @FormUrlEncoded
    @POST("MyRecords/Memo/getImageStorageContentNumber.php")
    fun getImageStorageContentNumber(
            @Field("date") date: String,
            @Field("time") time: String,
            @Field("cateNum") cateNum: Int
    ): Call<ContentNumberInfo>

}