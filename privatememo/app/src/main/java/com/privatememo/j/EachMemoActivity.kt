package com.privatememo.j

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.databinding.ActivityMainBinding
import com.privatememo.j.databinding.EachmemoactivityBinding
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.viewmodel.EachMemoViewModel
import com.privatememo.j.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.eachmemoactivity.*
import kotlinx.android.synthetic.main.memofragment.*
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

        var layoutmanager = LinearLayoutManager(EachMemoBinding.memoRcv.context)
        EachMemoBinding.memoRcv.layoutManager = layoutmanager
        EachMemoBinding.memoRcv.adapter = adapter

        backbutton.setOnClickListener {
            finish()
        }

        makememo.setOnClickListener {
            var intent = Intent(this, WriteMemoActivity::class.java)
            startActivity(intent)
        }
    }
}