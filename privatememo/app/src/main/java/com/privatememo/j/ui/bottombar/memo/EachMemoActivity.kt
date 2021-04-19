package com.privatememo.j.ui.bottombar.memo

import android.animation.ObjectAnimator
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.privatememo.j.R
import com.privatememo.j.adapter.*
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.EachmemoactivityBinding
import com.privatememo.j.utility.ApplyFontModule
import com.privatememo.j.utility.Utility
import com.privatememo.j.viewmodel.EachMemoViewModel
import kotlinx.android.synthetic.main.eachmemoactivity.*
import kotlinx.android.synthetic.main.memofragment.backbutton
import java.util.*
import kotlin.collections.ArrayList


class EachMemoActivity : AppCompatActivity() {

    lateinit var EachMemoBinding: EachmemoactivityBinding
    var eachMemoViewModel = EachMemoViewModel()
    var adapter = EachMemoAdapter()

    lateinit var EachMemoDialog: Dialog
    lateinit var progressDialog:ProgressDialog

    var font_list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(ApplyFontModule.a.FontCall())

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getRealSize(size)
        val width = size.x
        val height = size.y

        font_list.add("만든 날짜 순")
        font_list.add("수정 날짜 순")

        eachMemoViewModel = ViewModelProvider(this).get(EachMemoViewModel::class.java)
        EachMemoBinding = DataBindingUtil.setContentView(this, R.layout.eachmemoactivity)
        EachMemoBinding.setLifecycleOwner(this)
        EachMemoBinding.eachMemoViewModel = eachMemoViewModel

        progressDialog = ProgressDialog(EachMemoBinding.root.context, android.R.style.Theme_Material_Dialog_Alert)

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

        Utility.EachMemoFloating.FloatingState = 0
        eachMemoViewModel.search(Utility.EachMemoLoadMore.EachMemoMin, 10, Utility.EachMemoSort.SortState)

        backbutton.setOnClickListener {
            finish()
        }

        makememo.setOnClickListener {
            var intent = Intent(this, WriteMemoActivity::class.java)
            intent.putExtra("email", eachMemoViewModel.email)
            intent.putExtra("catenum", eachMemoViewModel.cateNum)
            startActivityForResult(intent,900)
        }

        var controler = Observer<Boolean> { result ->
            if(eachMemoViewModel.items.size == 0){
                EachMemoBinding.layout.visibility = View.VISIBLE
            }
            else{
                EachMemoBinding.layout.visibility = View.INVISIBLE
            }
            Thread.sleep(200)
            adapter.notifyDataSetChanged()
            progressDialog.dismiss()
            Log.i("tag","호출됩니까? 안될거같은데")

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

            override fun CalendarShortClick(holder: CalendarAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }


        var sortToggle = Observer<Boolean>{ result ->
            if(result == false){
                var objectAnimator: ObjectAnimator =
                        ObjectAnimator.ofFloat(EachMemoBinding.Sortcontent, "translationY",0.toFloat())
                objectAnimator.duration = 300
                objectAnimator.start()
            }
            else if(result == true){
                var objectAnimator: ObjectAnimator =
                        ObjectAnimator.ofFloat(EachMemoBinding.Sortcontent,"translationY",-height/2.toFloat())
                objectAnimator.duration = 300
                objectAnimator.start()
            }
        }
        eachMemoViewModel.sortToggle.observe(EachMemoBinding.lifecycleOwner!!,sortToggle)

        EachMemoBinding.sortlistView.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        Utility.EachMemoSort.SortState = 0
                    }
                    1 -> {
                        Utility.EachMemoSort.SortState = 1
                    }
                }
                eachMemoViewModel.controler.value = false
                eachMemoViewModel.search(Utility.EachMemoLoadMore.EachMemoMin,Utility.EachMemoLoadMore.EachMemoMax,Utility.EachMemoSort.SortState)
                //Toast.makeText(EachMemoBinding.root.context,"${FontModule.Font}로 변경", Toast.LENGTH_SHORT).show()
                eachMemoViewModel.sortToggle.value = false
            }
        })
        val ChangeTextSizeTitle_adapter: ArrayAdapter<String> = ArrayAdapter<String>(EachMemoBinding.root.context, android.R.layout.simple_list_item_1, font_list)
        EachMemoBinding.sortlistView.setAdapter(ChangeTextSizeTitle_adapter)




        EachMemoBinding.memoRcv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!EachMemoBinding.memoRcv.canScrollVertically(-1)) {
                    Log.i("CategoryFragment", "Top of list.")
                } else if (!EachMemoBinding.memoRcv.canScrollVertically(1)) {
                    Log.i("CategoryFragment", "End of list.")

                    if((Utility.EachMemoLoadMore.EachMemoMax > adapter.itemCount)){

                    }
                    else{
                        progressDialog.setMessage("Loading..")
                        progressDialog.show()

                        Utility.EachMemoLoadMore.EachMemoMid += 10
                        Utility.EachMemoLoadMore.EachMemoMax = Utility.EachMemoLoadMore.EachMemoMid + 10
                        eachMemoViewModel.whenScrolled(Utility.EachMemoLoadMore.EachMemoMid, Utility.EachMemoLoadMore.EachMemoMax, Utility.EachMemoSort.SortState)
                    }
                    Log.i("CategoryFragment","max: ${Utility.EachMemoLoadMore.EachMemoMax} mid: ${Utility.EachMemoLoadMore.EachMemoMid}")

                } else {
                    Log.i("CategoryFragment", "idle.")
                }
            }
        })

        EachMemoBinding.fabMain.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(Utility.EachMemoFloating.FloatingState == 0){
                    Utility.EachMemoFloating.FloatingState = 1
                }
                else if(Utility.EachMemoFloating.FloatingState == 1){
                    Utility.EachMemoFloating.FloatingState = 0
                }
                Collections.reverse(eachMemoViewModel.items)
            }
        })

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

        /*var withTime = Thread()
        withTime.run {
            try {
                Thread.sleep(200)
            }catch (e:Exception){

            }
        }*/



    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == 153){
            Log.i("tag","153호출")
            eachMemoViewModel.search(Utility.EachMemoLoadMore.EachMemoMin, Utility.EachMemoLoadMore.EachMemoMax, Utility.EachMemoSort.SortState)
        }

    }
}