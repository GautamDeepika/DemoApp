package com.example.demoapp.view.userprofile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.demoapp.R
import com.example.demoapp.databinding.ActivityUserBinding
import com.example.demoapp.viewmodel.userprofile.UserVM
import com.example.demoapp.viewmodel.userprofile.UserVMFactory
import java.lang.ref.WeakReference

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private val viewModel: UserVM by viewModels {
        UserVMFactory(WeakReference(this))
    }
    private lateinit var navController: NavController

    companion object {
        fun startActivity(context: Context) {
            if (context == null) return
            val intent = Intent(context, UserActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user)
        binding.viewModel = viewModel
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostUserFragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}