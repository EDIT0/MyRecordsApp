package com.privatememo.j.ui.bottombar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.privatememo.j.R
import kotlinx.android.synthetic.main.settingfragment.*

class SettingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.settingfragment, settingfrag, false)


        return rootView
    }
}