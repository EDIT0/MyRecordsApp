package com.privatememo.j.ui.bottombar.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.privatememo.j.R
import com.privatememo.j.ui.bottombar.MainActivity
import kotlinx.android.synthetic.main.settingfragment.*
import kotlinx.android.synthetic.main.settingfragment.view.*


class SettingFragment : Fragment() {

    var email = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.settingfragment, settingfrag, false)


        var act = activity as MainActivity
        email = act.mainViewModel.email.value?:""

        rootView.profile.setOnClickListener {
            var gotoProfile = Intent(rootView.context, ProfileManagement::class.java)
            gotoProfile.putExtra("email",email)
            startActivityForResult(gotoProfile,601)
        }

        rootView.changeTextSize.setOnClickListener {
            var gotoChangeTextSize = Intent(rootView.context, ProfileManagement::class.java)
            startActivityForResult(gotoChangeTextSize,602)
        }



        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var act = activity as MainActivity

        if(requestCode == 601 && resultCode == 601){
            val nickname = data?.getStringExtra("nickname")
            val motto = data?.getStringExtra("motto")
            act.mainViewModel.nickname.value = nickname
            act.mainViewModel.motto.value = motto
        }
        else if(requestCode == 602 && resultCode == 602){

        }
    }
}