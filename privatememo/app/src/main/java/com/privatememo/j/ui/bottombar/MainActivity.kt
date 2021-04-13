package com.privatememo.j.ui.bottombar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.privatememo.j.*
import com.privatememo.j.databinding.ActivityMainBinding
import com.privatememo.j.ui.bottombar.memo.MemoFragment
import com.privatememo.j.ui.bottombar.search.SearchFragment
import com.privatememo.j.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var fm: FragmentManager = supportFragmentManager
    lateinit var MainBinding: ActivityMainBinding
    var mainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        MainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        MainBinding.setLifecycleOwner(this)
        MainBinding.mainViewModel = mainViewModel



        //fm.beginTransaction().replace(R.id.framelayout, fragment2()).commit()
        with(fm.beginTransaction()){
            replace(
                R.id.framelayout,
                    MemoFragment()
            )
            commit()
        }

        bottom_tab.setOnNavigationItemSelectedListener(this)

        var getintent = getIntent();
        var getbundle = getintent.getExtras();

        mainViewModel.email.setValue(getbundle?.getString("email"))
        mainViewModel.nickname.setValue(getbundle?.getString("nickname"))
        mainViewModel.motto.setValue(getbundle?.getString("motto"))
        mainViewModel.picPath.setValue(getbundle?.getString("picPath"))

        Log.i("TAG", "넘어온 데이터1 ${getbundle?.getString("email")}, ${getbundle?.getString("nickname")} " +
                "${getbundle?.getString("motto")} ${getbundle?.getString("picPath")}")
        Log.i("TAG", "넘어온 데이터2 ${mainViewModel.email.getValue()}, ${mainViewModel.nickname.getValue()} ${mainViewModel.motto.getValue()} " +
                "${mainViewModel.picPath.getValue()}")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("혹시","이거 호출됨?")
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.toolbar_item_memo ->{
                fm.beginTransaction().replace(
                    R.id.framelayout,
                        MemoFragment()
                ).commit()

                return true
            }
            R.id.toolbar_item_search -> {
                fm.beginTransaction().replace(
                    R.id.framelayout,
                        SearchFragment()
                ).commit()
                return true
            }
            R.id.toolbar_item_calendar -> {
                fm.beginTransaction().replace(
                    R.id.framelayout,
                        CalendarFragment()
                ).commit()
                return true
            }
            R.id.toolbar_item_setting -> {
                fm.beginTransaction().replace(
                    R.id.framelayout,
                        SettingFragment()
                ).commit()
                return true
            }
        }
        return false
    }
}