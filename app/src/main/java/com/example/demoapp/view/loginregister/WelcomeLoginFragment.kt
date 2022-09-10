package com.example.demoapp.view.loginregister

import android.graphics.Paint
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.databinding.FragmentWelcomeLoginBinding
import com.example.demoapp.view.userprofile.UserActivity
import com.example.demoapp.viewmodel.loginregister.WelcomeLoginFragmentVM
import com.example.demoapp.viewmodel.loginregister.WelcomeLoginFragmentVMFactory
import com.example.myapplication.utils.ToastUtils
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

class WelcomeLoginFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeLoginBinding
    private val viewModel: WelcomeLoginFragmentVM by viewModels {
        WelcomeLoginFragmentVMFactory(WeakReference(context))
    }

    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = WelcomeLoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_login, container, false)
        binding.viewModel = viewModel
        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.tvRegister.paintFlags = binding.tvRegister.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.tvForgotPassword.paintFlags =
            binding.tvForgotPassword.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.tvRegister.setOnClickListener {
            EventBus.getDefault().post(LoginRegisterActivity.UpdateEvents.REGISTER_USER_FRAGMENT)
        }
        binding.tvForgotPassword.setOnClickListener {
            ToastUtils.showToastShort(requireContext(), "ForgotPasswordClicked")
        }
        binding.btnLogin.setOnClickListener {
            userLogin()
        }
    }

    private fun userLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNullOrEmpty()) {
            ToastUtils.showToastShort(requireContext(), "Email is required!")
            binding.etEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ToastUtils.showToastShort(requireContext(), "Please provide valid Email!")
            binding.etEmail.requestFocus()
            return
        }
        if (password.isNullOrEmpty()) {
            ToastUtils.showToastShort(requireContext(), "Password is required!")
            binding.etPassword.requestFocus()
            return
        }
        if (password.length < 3) {
            ToastUtils.showToastShort(requireContext(), "Min password length is 6 characters!")
            binding.etPassword.requestFocus()
            return
        }
        binding.progressbar.visibility = View.VISIBLE

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                UserActivity.startActivity(requireContext())
            } else {
                ToastUtils.showToastLong(
                    requireContext(),
                    "Failed to login! Please check your credentials"
                )
            }
        }
    }


//    override fun onDestroy() {
//        super.onDestroy()
//        EventBus.getDefault().unregister(this)
//    }
}