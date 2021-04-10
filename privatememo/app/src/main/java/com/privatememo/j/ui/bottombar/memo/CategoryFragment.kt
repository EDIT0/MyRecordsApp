package com.privatememo.j.ui.bottombar.memo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.R
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.CategoryfragmentBinding
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.categoryfragment.*
import java.lang.Exception

class CategoryFragment : Fragment() {

    lateinit var CategoryBinding: CategoryfragmentBinding
    var categoryViewModel = CategoryViewModel()
    var adapter = CategoryAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        CategoryBinding = DataBindingUtil.inflate(inflater, R.layout.categoryfragment, categoryfrag,false)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        CategoryBinding.setLifecycleOwner(this)
        CategoryBinding.categoryViewModel = categoryViewModel


        var act = activity as MainActivity
        Log.i("tag","이것은 카테고리프래그먼트의 이메일입니다. ${act.mainViewModel.email.value}")
        categoryViewModel.email.set(act.mainViewModel.email.value)

        var layoutmanager = LinearLayoutManager(CategoryBinding.cateRcv.context)
        CategoryBinding.cateRcv.layoutManager = layoutmanager
        CategoryBinding.cateRcv.adapter = adapter


        var controler = Observer<Boolean> { result ->
            if(categoryViewModel.items.size == 0){
                CategoryBinding.layout.visibility = View.VISIBLE
            }
            else{
                CategoryBinding.layout.visibility = View.INVISIBLE
            }
            var act = activity as MainActivity
            act.mainViewModel.totalCateNum.value = adapter.getItemCount()
        }
        categoryViewModel?.controler?.observe(CategoryBinding.lifecycleOwner!!, controler)


        adapter.itemClick = object : AdapterListener{
            override fun CategoryClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                var intent = Intent(context, EachMemoActivity::class.java)
                intent.putExtra("email", categoryViewModel.email.get())
                intent.putExtra("catenum", categoryViewModel.items.get(position).catenum)
                Log.i("tag","카테고리 넘버: "+categoryViewModel.items.get(position).catenum)
                intent.putExtra("catename", categoryViewModel.items.get(position).catename)
                startActivity(intent)
            }

            override fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }

        return CategoryBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("fragment1", "onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("fragment1", "onCreate()")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("fragment1", "onActivityCreated()")
    }

    override fun onStart() {
        super.onStart()
        Log.i("fragment1", "onStart()")

        categoryViewModel.search()

        var withTime = Thread()
        withTime.run {
            try {
                Thread.sleep(200)
            }catch (e: Exception){

            }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        Log.i("fragment1", "onResume()")
        var act = activity as MainActivity
        act.mainViewModel.totalCateNum.value = adapter.getItemCount()
    }

    override fun onPause() {
        super.onPause()
        Log.i("fragment1", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i("fragment1", "onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("fragment1", "onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("fragment1", "onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("fragment1", "onDetach()")
    }
}