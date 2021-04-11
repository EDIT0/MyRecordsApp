package com.privatememo.j.api

import android.view.View
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter

interface AdapterListener {

    fun CategoryClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)
    fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position:Int)
    fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position:Int)
    fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)

}