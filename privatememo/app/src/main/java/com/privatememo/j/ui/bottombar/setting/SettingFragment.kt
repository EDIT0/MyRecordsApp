package com.privatememo.j.ui.bottombar.setting

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.privatememo.j.R
import com.privatememo.j.ui.bottombar.MainActivity
import com.privatememo.j.ui.bottombar.memo.ReviseCategory
import com.privatememo.j.ui.login.WelcomeActivity
import com.privatememo.j.utility.ApplyFontModule
import kotlinx.android.synthetic.main.settingfragment.*
import kotlinx.android.synthetic.main.settingfragment.view.*


class SettingFragment : Fragment() {

    var email = ""
    lateinit var rootView: View

    lateinit var LogoutDialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getContext()?.getTheme()?.applyStyle(ApplyFontModule.a.FontCall(), true)
        rootView = inflater.inflate(R.layout.settingfragment, settingfrag, false)

        LogoutDialog = Dialog(rootView.context)
        LogoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        LogoutDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        LogoutDialog.setContentView(R.layout.logoutcustomdialog);
        var params: WindowManager.LayoutParams = LogoutDialog?.getWindow()?.getAttributes()!!
        params.width = 800
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        LogoutDialog?.getWindow()?.setAttributes(params)


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

        rootView.logout.setOnClickListener {
            showCustomDialog()
        }



        return rootView
    }

    fun showCustomDialog(){
        LogoutDialog.show();

        LogoutDialog.findViewById<TextView>(R.id.yes).setOnClickListener {
            //AutoLogin
            val sp = activity!!.getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE)
            val editor = sp.edit()
            editor.clear()
            editor.commit()

            var intent = Intent(context, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK + Intent.FLAG_ACTIVITY_SINGLE_TOP + Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            LogoutDialog.dismiss()
        }
        LogoutDialog.findViewById<TextView>(R.id.no).setOnClickListener {
            LogoutDialog.dismiss()
        }
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