package com.privatememo.j.utility

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.privatememo.j.R
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.datamodel.OnlyPicInfo2
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.ui.bottombar.memo.ReviseCategory

object Utility {

    lateinit var CustomDialog: Dialog

    @BindingAdapter("memo_profile_image", "error")
    @JvmStatic
    fun Profile_image (iv : ImageView, url : String?, error : Drawable){
        Log.i("유틸리티", "$url , $error")
        Glide.with(iv.context).load(url).apply(RequestOptions.circleCropTransform()).override(1000,1000).error(error).into(iv)
    }

    @BindingAdapter("category_rcv")
    @JvmStatic
    fun CategoryRcv (rcv: RecyclerView, items : ArrayList<CategoryInfo2>){
        (rcv.adapter as CategoryAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("memo_rcv")
    @JvmStatic
    fun MemoRcv (rcv: RecyclerView, items : ArrayList<MemoInfo2>){
        (rcv.adapter as EachMemoAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("category_rcv_image", "error")
    @JvmStatic
    fun CategoryRcvImage (iv : ImageView, url : String?, error : Drawable){
        Glide.with(iv.context).load(url).apply(RequestOptions.circleCropTransform()).override(1000,1000).error(error).into(iv)
    }

    @BindingAdapter("onlypic_rcv")
    @JvmStatic
    fun OnlyPicRcv (rcv: RecyclerView, items : ArrayList<OnlyPicInfo2>){
        (rcv.adapter as OnlyPicAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("onlypic_rcv_image", "error")
    @JvmStatic
    fun OnlyPicRcvImage (iv : ImageView, url : String?, error : Drawable){
        Glide.with(iv.context).load(url).override(1000,1000).error(error).into(iv)
    }

}