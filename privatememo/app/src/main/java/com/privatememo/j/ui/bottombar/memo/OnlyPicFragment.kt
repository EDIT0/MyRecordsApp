package com.privatememo.j.ui.bottombar.memo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.adapter.CalendarAdapter
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.R
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.adapter.SearchAdapter
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.OnlypicfragmentBinding
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.utility.ApplyFontModule
import com.privatememo.j.utility.Utility
import com.privatememo.j.viewmodel.OnlyPicViewModel
import kotlinx.android.synthetic.main.onlypicfragment.*

class OnlyPicFragment : Fragment() {

    lateinit var OnlypicfragmentBinding: OnlypicfragmentBinding
    var onlyPicViewModel = OnlyPicViewModel()
    var adapter = OnlyPicAdapter()

    lateinit var OnlyPicDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getContext()?.getTheme()?.applyStyle(ApplyFontModule.a.FontCall(), true)
        OnlypicfragmentBinding = DataBindingUtil.inflate(inflater, R.layout.onlypicfragment, onlypicfrag,false)
        onlyPicViewModel = ViewModelProvider(this).get(OnlyPicViewModel::class.java)
        OnlypicfragmentBinding.setLifecycleOwner(this)
        OnlypicfragmentBinding.onlyPicViewModel = onlyPicViewModel

        OnlyPicDialog = Utility.NormalDialogSetting(OnlypicfragmentBinding.root.context, R.layout.onlypiccustomdialog, 600)

        var act = activity as MainActivity
        Log.i("tag","이것은 온리픽프래그먼트의 이메일입니다. ${act.mainViewModel.email.value}")
        onlyPicViewModel.email.set(act.mainViewModel.email.value)

        var layoutmanager = GridLayoutManager(OnlypicfragmentBinding.root.context, 3, LinearLayoutManager.VERTICAL, false)
        OnlypicfragmentBinding.onlypicRcv.layoutManager = layoutmanager
        OnlypicfragmentBinding.onlypicRcv.adapter = adapter

        OnlypicfragmentBinding.loadmore.setOnClickListener {
            with(Utility.OnlyPicLoadMore){
                if(OnlyPicMax < 6){
                    if(isChanged > 0){
                        onlyPicViewModel.search(0, OnlyPicMax+isChanged)
                        isChanged = 0
                    }
                }
                else {
                    OnlyPicMid += 6
                    OnlyPicMax = OnlyPicMid + 6
                    onlyPicViewModel.whenScrolled(OnlyPicMid, OnlyPicMax)
                }
            }
        }


        var controler = Observer<Boolean> { result ->
            if(onlyPicViewModel.items.size == 0){
                OnlypicfragmentBinding.layout.visibility = View.VISIBLE
            }
            else{
                OnlypicfragmentBinding.layout.visibility = View.INVISIBLE
            }
        }
        onlyPicViewModel?.controler?.observe(OnlypicfragmentBinding.lifecycleOwner!!, controler)


        adapter.itemClick = object : AdapterListener {
            override fun CategoryShortClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {

            }

            override fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun CategoryImageClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {

            }

            override fun CategoryLongClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {

            }

            override fun OnlyPicShortClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position: Int) {
                var intent = Intent(OnlypicfragmentBinding.root.context, ShowAndReviseMemo::class.java)
                var bundle = Bundle()
                bundle.putInt("contentNum",onlyPicViewModel.items.get(position).contentnum)
                bundle.putString("title",onlyPicViewModel.items.get(position).title)
                bundle.putString("memo",onlyPicViewModel.items.get(position).memo)
                bundle.putString("date",onlyPicViewModel.items.get(position).date)
                bundle.putString("revisedate",onlyPicViewModel.items.get(position).revicedate)
                bundle.putString("time",onlyPicViewModel.items.get(position).time)
                bundle.putString("revisetime",onlyPicViewModel.items.get(position).revicetime)
                bundle.putString("ConBookmark",onlyPicViewModel.items.get(position).ConBookmark)
                bundle.putString("email",onlyPicViewModel.email.get().toString())
                bundle.putInt("cateNum",onlyPicViewModel.items.get(position).category_catenum)

                Log.i("tag", "보내는 데이터 ${onlyPicViewModel.items.get(position).contentnum} ${onlyPicViewModel.items.get(position).title}")
                intent.putExtras(bundle)
                startActivityForResult(intent, 500)
            }

            override fun OnlyPicLongClick(holder: OnlyPicAdapter.ViewHolder?, view: View?, position: Int) {
                showDialog(position)
            }

            override fun SearchShortClick(holder: SearchAdapter.ViewHolder?, view: View?, position: Int
            ) {
                TODO("Not yet implemented")
            }

            override fun SearchLongClick(holder: SearchAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun CalendarShortClick(holder: CalendarAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }

        return OnlypicfragmentBinding.root
    }

    fun showDialog(position: Int){
        OnlyPicDialog.show();

        OnlyPicDialog.findViewById<TextView>(R.id.onlypicDelete).setOnClickListener {
            onlyPicViewModel.deleteMemo_call(onlyPicViewModel.items.get(position).contentnum)
            OnlyPicDialog.dismiss()
            onlyPicViewModel.items.removeAt(position)
            var act = activity as MainActivity
            act.mainViewModel.getMemoCount_call()
            adapter.notifyDataSetChanged()
        }
        OnlyPicDialog.findViewById<TextView>(R.id.finish).setOnClickListener {
            OnlyPicDialog.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        with(Utility.OnlyPicLoadMore){
            if(FirstStart == 1) {
                FirstStart = 0
                onlyPicViewModel.search(OnlyPicMin, OnlyPicMax+ isChanged)
                isChanged = 0
                Log.i("tag","온리픽프레그먼트의 getOnlyPic 호출")
            }
        }

        Log.i("tag","온리픽프레그먼트 온스타트")
    }

    override fun onResume() {
        super.onResume()
        Log.i("tag","온리픽프레그먼트 온리숨")
    }
}