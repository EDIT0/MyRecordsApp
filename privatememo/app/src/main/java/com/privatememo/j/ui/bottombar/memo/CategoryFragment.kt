package com.privatememo.j.ui.bottombar.memo

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.adapter.CalendarAdapter
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.R
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.adapter.SearchAdapter
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.CategoryfragmentBinding
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.utility.WholeImageActivity
import com.privatememo.j.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.categoryfragment.*

class CategoryFragment : Fragment() {

    lateinit var CategoryBinding: CategoryfragmentBinding
    var categoryViewModel = CategoryViewModel()
    var adapter = CategoryAdapter()

    lateinit var CategoryDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        CategoryBinding = DataBindingUtil.inflate(inflater, R.layout.categoryfragment, categoryfrag,false)
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        CategoryBinding.setLifecycleOwner(this)
        CategoryBinding.categoryViewModel = categoryViewModel

        CategoryDialog = Dialog(CategoryBinding.root.context)
        CategoryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        CategoryDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        CategoryDialog.setContentView(R.layout.categorycustomdialog);
        var params: WindowManager.LayoutParams = CategoryDialog?.getWindow()?.getAttributes()!!
        params.width = 600
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        CategoryDialog?.getWindow()?.setAttributes(params)



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
            override fun CategoryShortClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
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

            override fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                val intent = Intent(CategoryBinding.root.context, WholeImageActivity::class.java)
                intent.putExtra("imageUri", categoryViewModel.items.get(position).catepicPath)
                startActivity(intent)
            }

            override fun CategoryLongClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                showDialog(position)
            }

            override fun OnlyPicShortClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun OnlyPicLongClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun SearchShortClick(holder: SearchAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun SearchLongClick(holder: SearchAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun CalendarShortClick(holder: CalendarAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }

        return CategoryBinding.root
    }

    fun showDialog(position: Int){
        CategoryDialog.show();

        CategoryDialog.findViewById<TextView>(R.id.CateRevise).setOnClickListener {
            var intent = Intent(CategoryBinding.root.context, ReviseCategory::class.java)
            var bundle = Bundle()

            bundle.putInt("cateNum",Integer.parseInt(categoryViewModel.items.get(position).catenum))
            bundle.putString("catename",categoryViewModel.items.get(position).catename)
            bundle.putString("explanation",categoryViewModel.items.get(position).explanation)
            bundle.putString("catepicPath",categoryViewModel.items.get(position).catepicPath)
            bundle.putString("email",categoryViewModel.items.get(position).memberlist_email)
            bundle.putString("CateBookmark",categoryViewModel.items.get(position).CateBookmark)

            intent.putExtras(bundle)
            startActivityForResult(intent, 900)
            CategoryDialog.dismiss()
        }
        CategoryDialog.findViewById<TextView>(R.id.CateDelete).setOnClickListener {
            categoryViewModel.DeleteCategory(position)
            CategoryDialog.dismiss()
            categoryViewModel.items.removeAt(position)
            var act = activity as MainActivity
            act.mainViewModel.totalCateNum.value = adapter.getItemCount()
            act.mainViewModel.getMemoCount_call()
            adapter.notifyDataSetChanged()
            act.mainViewModel.search()
        }
        CategoryDialog.findViewById<TextView>(R.id.finish).setOnClickListener {
            CategoryDialog.dismiss()
        }
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

        var act = activity as MainActivity
        categoryViewModel.search()
        act.mainViewModel.getMemoCount_call()


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
        act.mainViewModel.totalConNum.value = act.mainViewModel.totalConNum.value
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