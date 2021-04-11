package com.privatememo.j.ui.bottombar.memo

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eachMemoViewModel = ViewModelProvider(this).get(EachMemoViewModel::class.java)
        EachMemoBinding = DataBindingUtil.setContentView(this, R.layout.eachmemoactivity)
        EachMemoBinding.setLifecycleOwner(this)
        EachMemoBinding.eachMemoViewModel = eachMemoViewModel

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
            /*var act = activity as MainActivity
            act.mainViewModel.totalConNum.value = adapter.getItemCount()*/
        }
        eachMemoViewModel?.controler?.observe(EachMemoBinding.lifecycleOwner!!, controler)

        adapter.itemClick = object : AdapterListener {
            override fun CategoryClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {

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
                var deletedialog = AlertDialog.Builder(EachMemoBinding.root.context)
                        .setTitle("알림")
                        .setMessage("삭제하시겠습니까?")
                        .setPositiveButton("네", DialogInterface.OnClickListener { dialog, which ->
                            eachMemoViewModel.deleteMemo_call(eachMemoViewModel.items.get(position).contentnum)
                            eachMemoViewModel.items.removeAt(position)
                            adapter.notifyDataSetChanged()
                        })
                        .setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, which ->

                        })
                        .create()

                deletedialog.show()
            }

            override fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

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