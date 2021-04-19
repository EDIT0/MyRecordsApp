package com.privatememo.j.utility

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.privatememo.j.adapter.*
import com.privatememo.j.datamodel.CategoryInfo2
import com.privatememo.j.datamodel.MemoInfo2
import com.privatememo.j.datamodel.OnlyPicInfo2
import com.privatememo.j.datamodel.SearchInfo2
import java.util.*
import kotlin.collections.ArrayList


object Utility {

    lateinit var CustomDialog: Dialog

    @BindingAdapter("memo_profile_image", "error")
    @JvmStatic
    fun Profile_image (iv : ImageView, url : String?, error : Drawable){
        Glide.with(iv.context).load(url+ "? ${Date().getTime()}").apply(RequestOptions.circleCropTransform()).override(1000, 1000).error(error).into(iv)
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
        Glide.with(iv.context).load(url+ "? ${Date().getTime()}").apply(RequestOptions.circleCropTransform()).override(1000,1000).error(error).into(iv)
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
        Glide.with(iv.context).load(url+ "? ${Date().getTime()}").override(1000,1000).error(error).into(iv)
    }

    @BindingAdapter("search_rcv")
    @JvmStatic
    fun SearchRcv (rcv: RecyclerView, items : ArrayList<SearchInfo2>){
        (rcv.adapter as SearchAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }

    @BindingAdapter("calendar_rcv")
    @JvmStatic
    fun CalendarRcv (rcv: RecyclerView, items : ArrayList<MemoInfo2>){
        (rcv.adapter as CalendarAdapter).items = items
        rcv.adapter?.notifyDataSetChanged()
    }

    object EachMemoLoadMore{
        var EachMemoMin = 0
        var EachMemoMid = 0
        var EachMemoMax = 10
    }

    object EachMemoSort{
        var SortState = 0
    }

    object EachMemoFloating{
        var FloatingState = 0
    }

    object SearchLoadMore{
        var SearchMin = 0
        var SearchMid = 0
        var SearchMax = 10
    }

    object OnlyPicLoadMore{
        var OnlyPicMin = 0
        var OnlyPicMid = 0
        var OnlyPicMax = 6
        var FirstStart = 1
        var isChanged = 0
    }
}