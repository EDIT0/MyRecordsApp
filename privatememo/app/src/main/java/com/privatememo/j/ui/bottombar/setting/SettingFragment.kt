package com.privatememo.j.ui.bottombar.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.privatememo.j.ChangeFont
import com.privatememo.j.R
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.ui.bottombar.memo.MemoFragment
import com.privatememo.j.utility.ApplyFontModule
import com.privatememo.j.utility.MemberSettingModule
import kotlinx.android.synthetic.main.settingfragment.*
import kotlinx.android.synthetic.main.settingfragment.view.*


class SettingFragment : Fragment() {

    var email = ""
    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getContext()?.getTheme()?.applyStyle(ApplyFontModule.a.FontCall(), true)
        rootView = inflater.inflate(R.layout.settingfragment, settingfrag, false)


        var act = activity as MainActivity
        email = act.mainViewModel.email.value?:""

        rootView.profile.setOnClickListener {
            var gotoProfile = Intent(rootView.context, ProfileManagement::class.java)
            gotoProfile.putExtra("email",email)
            startActivityForResult(gotoProfile,601)
        }

        rootView.changeTextSize.setOnClickListener {
            var gotoChangeTextSize = Intent(rootView.context, ChangeTextSize::class.java)
            gotoChangeTextSize.putExtra("email",email)
            startActivityForResult(gotoChangeTextSize,602)
        }

        rootView.changeFont.setOnClickListener {
            var gotoChangeFont = Intent(rootView.context, ChangeFont::class.java)
            gotoChangeFont.putExtra("email",email)
            startActivityForResult(gotoChangeFont,603)
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
        else if(requestCode == 603 && resultCode == 6031){
            var act = activity as MainActivity
            Log.i("tag","603호출")
            var intent = Intent(context,MainActivity::class.java)
            intent.putExtra("email",act.mainViewModel.email.value.toString())
            intent.putExtra("nickname",act.mainViewModel.nickname.value.toString())
            intent.putExtra("motto",act.mainViewModel.motto.value.toString())
            intent.putExtra("picPath",act.mainViewModel.picPath.value.toString())
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}