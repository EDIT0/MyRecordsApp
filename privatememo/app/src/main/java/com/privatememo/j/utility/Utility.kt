package com.privatememo.j.utility

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object Utility {

    @BindingAdapter("main_rcv_image", "error")
    @JvmStatic
    fun ProfilePicture (iv : ImageButton, url : String?, error : Drawable){
        var image_baseurl = "http://image.tmdb.org/t/p/original/"
        Glide.with(iv.context).load(image_baseurl + url).override(500,500).error(error).into(iv)
    }

}