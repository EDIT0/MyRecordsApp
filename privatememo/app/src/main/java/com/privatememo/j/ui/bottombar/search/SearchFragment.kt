package com.privatememo.j.ui.bottombar.search

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.privatememo.j.adapter.CalendarAdapter
import com.privatememo.j.R
import com.privatememo.j.adapter.SearchAdapter
import com.privatememo.j.adapter.CategoryAdapter
import com.privatememo.j.adapter.EachMemoAdapter
import com.privatememo.j.adapter.OnlyPicAdapter
import com.privatememo.j.api.AdapterListener
import com.privatememo.j.databinding.SearchfragmentBinding
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.ui.bottombar.memo.ShowAndReviseMemo
import com.privatememo.j.utility.ApplyFontModule
import com.privatememo.j.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.searchfragment.*


class SearchFragment : Fragment() {

    lateinit var SearchfragmentBinding: SearchfragmentBinding
    var searchViewModel = SearchViewModel()
    var adapter = SearchAdapter()

    lateinit var SearchDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getContext()?.getTheme()?.applyStyle(ApplyFontModule.a.FontCall(), true)
        SearchfragmentBinding = DataBindingUtil.inflate(inflater, R.layout.searchfragment, searchfrag,false)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        SearchfragmentBinding.setLifecycleOwner(this)
        SearchfragmentBinding.searchViewModel = searchViewModel

        SearchDialog = Dialog(SearchfragmentBinding.root.context)
        SearchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        SearchDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        SearchDialog.setContentView(R.layout.onlypiccustomdialog);
        var params: WindowManager.LayoutParams = SearchDialog?.getWindow()?.getAttributes()!!
        params.width = 600
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        SearchDialog?.getWindow()?.setAttributes(params)

        var layoutmanager = LinearLayoutManager(SearchfragmentBinding.searchRcv.context)
        SearchfragmentBinding.searchRcv.layoutManager = layoutmanager
        SearchfragmentBinding.searchRcv.adapter = adapter

        var act = activity as MainActivity
        searchViewModel.email.set(act.mainViewModel.email.value)
        Log.i("tag","Search Email: ${act.mainViewModel.email.value}")


        var keypad = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        SearchfragmentBinding.edittext.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if(SearchfragmentBinding.edittext.text.length >= 1) {
                    searchViewModel.getSearchResult_call()
                    searchViewModel.items.clear()
                    adapter.notifyDataSetChanged()
                    try {
                        keypad.hideSoftInputFromWindow(
                            activity!!.currentFocus!!.windowToken,
                            InputMethodManager.HIDE_NOT_ALWAYS
                        )
                    } catch (e: Exception) {
                    }
                }
                else{
                    Toast.makeText(SearchfragmentBinding.root.context, "한 글자 이상 검색해주세요.",Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })

        var controler = Observer<Boolean> { result ->
            if(searchViewModel.items.size == 0){
                SearchfragmentBinding.layout.visibility = View.VISIBLE
            }
            else{
                SearchfragmentBinding.layout.visibility = View.INVISIBLE
            }
        }
        searchViewModel?.controler?.observe(SearchfragmentBinding.lifecycleOwner!!, controler)


        adapter.itemClick = object : AdapterListener {
            override fun CategoryShortClick(holder: CategoryAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

            override fun EachMemoShortClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {

            }

            override fun EachMemoLongClick(holder: EachMemoAdapter.ViewHolder?, view: View?, position: Int) {

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
                var intent = Intent(SearchfragmentBinding.root.context, ShowAndReviseMemo::class.java)
                var bundle = Bundle()
                bundle.putInt("contentNum",searchViewModel.items.get(position).contentnum)
                bundle.putString("title",searchViewModel.items.get(position).title)
                bundle.putString("memo",searchViewModel.items.get(position).memo)
                bundle.putString("date",searchViewModel.items.get(position).date)
                bundle.putString("revisedate",searchViewModel.items.get(position).revicedate)
                bundle.putString("time",searchViewModel.items.get(position).time)
                bundle.putString("revisetime",searchViewModel.items.get(position).revicetime)
                bundle.putString("ConBookmark",searchViewModel.items.get(position).ConBookmark)
                bundle.putString("email",searchViewModel.items.get(position).memberlist_email)
                bundle.putInt("cateNum",searchViewModel.items.get(position).category_catenum)

                Log.i("tag", "보내는 데이터 ${searchViewModel.items.get(position).contentnum} ${searchViewModel.items.get(position).title}")
                intent.putExtras(bundle)
                startActivityForResult(intent, 600)
            }

            override fun SearchLongClick(holder: SearchAdapter.ViewHolder?, view: View?, position: Int) {
                showCustomDialog(position)
            }

            override fun CalendarShortClick(holder: CalendarAdapter.ViewHolder?, view: View?, position: Int) {
                TODO("Not yet implemented")
            }

        }



        return SearchfragmentBinding.root
    }

    fun showCustomDialog(position: Int){
        SearchDialog.show();

        SearchDialog.findViewById<TextView>(R.id.onlypicDelete).setOnClickListener {
            searchViewModel.deleteMemo_call(searchViewModel.items.get(position).contentnum)
            SearchDialog.dismiss()
            searchViewModel.items.removeAt(position)
            adapter.notifyDataSetChanged()
        }
        SearchDialog.findViewById<TextView>(R.id.finish).setOnClickListener {
            SearchDialog.dismiss()
        }
    }

}