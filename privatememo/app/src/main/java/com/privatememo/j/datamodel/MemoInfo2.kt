package com.privatememo.j.datamodel

data class MemoInfo2(
    var contentnum: Int,
    var title: String,
    var memo: String,
    var date: String,
    var revicedate: String,
    var time: String,
    var revicetime: String,
    var ConBookmark: String,
    var memberlist_email: String,
    var category_catenum: Int
) {


    fun printDate(): String{
        var re_date = date.split("_")
        return re_date[0] + "년 " + re_date[1] + "월 " + re_date[2] +"일"
    }

    fun printReViseDate(): String{
        var re_revisedate = revicedate.split("_")
        if(re_revisedate.contains("20"))
            return re_revisedate[0] + "년 " + re_revisedate[1] + "월 " + re_revisedate[2] +"일"
        else{
            return ""
        }
    }

    fun printTime(): String{
        var re_time = time.split("_")
        return re_time[0] + "시 " + re_time[1] + "분 " + re_time[2] +"초"
    }
}