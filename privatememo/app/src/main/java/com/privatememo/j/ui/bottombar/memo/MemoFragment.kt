package com.privatememo.j.ui.bottombar.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.privatememo.j.R
import com.privatememo.j.utility.WholeImageActivity
import com.privatememo.j.adapter.MemoViewPagerAdapter
import com.privatememo.j.databinding.MemofragmentBinding
import com.privatememo.j.ui.bottombar.MainActivity
import kotlinx.android.synthetic.main.memofragment.*

class MemoFragment : Fragment() {

    lateinit var MemoBinding: MemofragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        MemoBinding = DataBindingUtil.inflate(inflater, R.layout.memofragment, memofrag,false)
        var act = activity as MainActivity
        MemoBinding.setLifecycleOwner(this)
        MemoBinding.memoViewModel = act.mainViewModel

        MemoBinding.makeCategory.setOnClickListener{
            var intent = Intent(context, MakeCategory::class.java)
            intent.putExtra("email", act.mainViewModel.email.value)
            Log.i("tag", "MakeCategory로 가는 길:  ${act.mainViewModel.email.value}")
            startActivity(intent)
        }

        MemoBinding.profilePicture.setOnClickListener {
            val intent = Intent(MemoBinding.root.context, WholeImageActivity::class.java)
            intent.putExtra("imageUri", act.mainViewModel.picPath.value)
            startActivity(intent)
        }

        return MemoBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var adapter = MemoViewPagerAdapter(requireActivity())
        viewpager_setting(adapter)
        tablayout_setting()
    }

    fun viewpager_setting(adapter : MemoViewPagerAdapter){
        viewpager.adapter = adapter
        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager.offscreenPageLimit = 2

        //화면 돌아갈 시 콜백
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                println("$position")
                //indicator.animatePageSelected(position)
            }
        })
    }

    fun tablayout_setting(){

        //viewpager와 tablayout 연결
        TabLayoutMediator(tabs, viewpager) { tab, position ->

            when(position) {
                0 -> {
                    tab.setIcon(R.drawable.ic_baseline_format_list_bulleted_24)
                }
                1 -> {
                    tab.setIcon(R.drawable.ic_baseline_grid_on_24)
                }
            }
        }.attach()

    }
}