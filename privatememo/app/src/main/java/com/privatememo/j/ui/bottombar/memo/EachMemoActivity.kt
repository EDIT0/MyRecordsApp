package com.privatememo.j.ui.bottombar.memo

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.R
import com.privatememo.j.adapter.SearchAdapter
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.EachmemoactivityBinding
import com.privatememo.j.viewmodel.EachMemoViewModel
import kotlinx.android.synthetic.main.eachmemoactivity.*
import kotlinx.android.synthetic.main.memofragment.backbutton


class EachMemoActivity : AppCompatActivity() {

    lateinit var EachMemoBinding: EachmemoactivityBinding
    var eachMemoViewModel = EachMemoViewModel()
    var adapter = EachMemoAdapter()

    lateinit var EachMemoDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eachMemoViewModel = ViewModelProvider(this).get(EachMemoViewModel::class.java)
        EachMemoBinding = DataBindingUtil.setContentView(this, R.layout.eachmemoactivity)
        EachMemoBinding.setLifecycleOwner(this)
        EachMemoBinding.eachMemoViewModel = eachMemoViewModel

        EachMemoDialog = Dialog(EachMemoBinding.root.context)
        EachMemoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        EachMemoDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        EachMemoDialog.setContentView(R.layout.onlypiccustomdialog);
        var params: WindowManager.LayoutParams = EachMemoDialog?.getWindow()?.getAttributes()!!
        params.width = 600
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        EachMemoDialog?.getWindow()?.setAttributes(params)

        var getintent = getIntent()
        eachMemoViewModel.email = getintent.getStringExtra("email")!!
        eachMemoViewModel.cateName = getintent.getStringExtra("catename")!!
        eachMemoViewModel.cateNum = getintent.getStringExtra("catenum")!!

        var layoutmanager = LinearLayoutManager(EachMemoBinding.memoRcv.context)
        EachMemoBinding.memoRcv.layoutManager = layoutmanager
        EachMemoBinding.memoRcv.adapter = adapter

        backbutton.setOnClickListener {
            finish()
        }

        makememo.setOnClickListener {
            var intent = Intent(this, WriteMemoActivity::class.java)
            intent.putExtra("email", eachMemoViewModel.email)
            intent.putExtra("catenum", eachMemoViewModel.cateNum)
            startActivity(intent)
        }

        var controler = Observer<Boolean> { result ->
            if(eachMemoViewModel.items.size == 0){
                EachMemoBinding.layout.visibility = View.VISIBLE
            }
            else{
                EachMemoBinding.layout.visibility = View.INVISIBLE
            }
        }
        eachMemoViewModel?.controler?.observe(EachMemoBinding.lifecycleOwner!!, controler)

        adapter.itemClick = object : AdapterListener {
            override fun CategoryShortClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                var intent = Intent(EachMemoBinding.root.context, ShowAndReviseMemo::class.java)
                var bundle = Bundle()
                bundle.putInt("contentNum",eachMemoViewModel.items.get(position).contentnum)
                bundle.putString("title",eachMemoViewModel.items.get(position).title)
                bundle.putString("memo",eachMemoViewModel.items.get(position).memo)
                bundle.putString("date",eachMemoViewModel.items.get(position).date)
                bundle.putString("revisedate",eachMemoViewModel.items.get(position).revicedate)
                bundle.putString("time",eachMemoViewModel.items.get(position).time)
                bundle.putString("revisetime",eachMemoViewModel.items.get(position).revicetime)
                bundle.putString("ConBookmark",eachMemoViewModel.items.get(position).ConBookmark)
                bundle.putString("email",eachMemoViewModel.items.get(position).memberlist_email)
                bundle.putInt("cateNum",eachMemoViewModel.items.get(position).category_catenum)

                Log.i("tag", "보내는 데이터 ${eachMemoViewModel.items.get(position).contentnum} ${eachMemoViewModel.items.get(position).title}")
                intent.putExtras(bundle)
                startActivityForResult(intent, 900)
            }

            override fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                showCustomDialog(position)
            }

            override fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun CategoryLongClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
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

        }
    }

    fun showCustomDialog(position: Int){
        EachMemoDialog.show();

        EachMemoDialog.findViewById<TextView>(R.id.onlypicDelete).setOnClickListener {
            eachMemoViewModel.deleteMemo_call(eachMemoViewModel.items.get(position).contentnum)
            EachMemoDialog.dismiss()
            eachMemoViewModel.items.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        EachMemoDialog.findViewById<TextView>(R.id.finish).setOnClickListener {
            EachMemoDialog.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("tag","온스타트 onStart()")
        eachMemoViewModel.search()

        var withTime = Thread()
        withTime.run {
            try {
                Thread.sleep(200)
            }catch (e:Exception){

            }
        }

        adapter.notifyDataSetChanged()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}