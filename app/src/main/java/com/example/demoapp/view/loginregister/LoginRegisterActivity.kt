package com.example.demoapp.view.loginregister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.demoapp.R
import com.example.demoapp.databinding.ActivityLoginRegisterBinding
import com.example.demoapp.viewmodel.loginregister.LoginRegisterVM
import com.example.demoapp.viewmodel.loginregister.LoginRegisterVMFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginRegisterBinding
    private val viewModel: LoginRegisterVM by viewModels {
        LoginRegisterVMFactory(WeakReference(this))
    }
    private val navController: NavController by lazy {
        findNavController(R.id.navHostLoginRegisterFragment)
    }

    enum class UpdateEvents {
        WELCOME_LOGIN_FRAGMENT,
        REGISTER_USER_FRAGMENT
    }


    companion object {
        fun startActivity(context: Context?) {
            if (context == null) return
            val intent = Intent(context, LoginRegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_register)
        binding.viewModel = viewModel
        supportActionBar?.hide()

//        //configure the navigation
//        val graph = navController.navInflater.inflate(R.navigation.nav_graph_login_register)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun updateEvents(updateEvents: UpdateEvents) {
        when (updateEvents) {
            UpdateEvents.REGISTER_USER_FRAGMENT -> {
                navController.navigate(R.id.navigation_register_user)
            }
            UpdateEvents.WELCOME_LOGIN_FRAGMENT -> {
                navController.navigate(R.id.navigation_welcome_login)
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}