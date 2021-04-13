package com.privatememo.j.utility

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.privatememo.j.adapter.SearchAdapter
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.datamodel.OnlyPicInfo2
import com.privatememo.j.datamodel.SearchInfo2

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

    @BindingAdapter("search_rcv")
    @JvmStatic
    fun SearchRcv (rcv: RecyclerView, items : ArrayList<SearchInfo2>){
        (rcv.adapter as SearchAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }
}