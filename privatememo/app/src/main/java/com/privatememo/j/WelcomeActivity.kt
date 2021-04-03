package com.privatememo.j

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.privatememo.j.databinding.WelcomeactivityBinding
import com.privatememo.j.viewmodel.WelcomeViewModel

class WelcomeActivity : AppCompatActivity() {

    lateinit var WelcomeBinding: WelcomeactivityBinding
    var welcomeViewModel = WelcomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingPermission()

        welcomeViewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        WelcomeBinding = DataBindingUtil.setContentView(this,R.layout.welcomeactivity)
        WelcomeBinding.setLifecycleOwner(this)
        WelcomeBinding.welcomeViewModel = welcomeViewModel

        WelcomeBinding.signup.setOnClickListener{
            var intent = Intent(this, SignUpActivity::class.java)
            startActivityForResult(intent, RESULT_OK)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Activity.RESULT_OK){
            Log.i("TAG", "웰컴")
        }
    }

    fun settingPermission(){
        var permis = object  : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                //Toast.makeText(this@WelcomeActivity, "권한 허가", Toast.LENGTH_SHORT) .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                //Toast.makeText(this@WelcomeActivity, "권한 거부", Toast.LENGTH_SHORT) .show()
                ActivityCompat.finishAffinity(this@WelcomeActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .setPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }
}