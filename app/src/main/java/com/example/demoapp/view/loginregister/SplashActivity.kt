package com.example.demoapp.view.loginregister

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.demoapp.R
import com.example.demoapp.databinding.ActivitySplashBinding
import com.example.demoapp.view.userprofile.UserActivity
import com.example.demoapp.viewmodel.loginregister.SplashVM
import com.example.demoapp.viewmodel.loginregister.SplashVMFactory
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashVM by viewModels {
        SplashVMFactory(WeakReference(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        viewModel.startNextActivity.observe(this, Observer {
            if (it) {
                UserActivity.startActivity(this)
                finish()
            }
        })
    }

}