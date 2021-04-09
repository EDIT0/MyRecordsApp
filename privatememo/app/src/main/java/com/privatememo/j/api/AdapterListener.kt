package com.privatememo.j.api

import android.view.View
import com.privatememo.j.adapter.CategoryAdapter

interface AdapterListener {

    fun CategoryClick(holder: CategoryAdapter.ViewHolder?, view: View?, position:Int)

}