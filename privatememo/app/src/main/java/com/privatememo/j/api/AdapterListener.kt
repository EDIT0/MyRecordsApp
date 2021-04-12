package com.privatememo.j.api

import android.view.View
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter

interface AdapterListener {

    fun CategoryShortClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)
    fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position:Int)
    fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position:Int)
    fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)
    fun CategoryLongClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)
    fun OnlyPicShortClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position:Int)
    fun OnlyPicLongClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position:Int)

}