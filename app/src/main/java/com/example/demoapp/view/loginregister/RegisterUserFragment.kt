package com.example.demoapp.view.loginregister

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.data.models.User
import com.example.demoapp.databinding.FragmentRegisterUserBinding
import com.example.demoapp.viewmodel.loginregister.RegisterUserFragmentVM
import com.example.demoapp.viewmodel.loginregister.RegisterUserFragmentVMFactory
import com.example.myapplication.utils.ToastUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.greenrobot.eventbus.EventBus
import java.lang.ref.WeakReference

class RegisterUserFragment : Fragment() {

    private lateinit var binding: FragmentRegisterUserBinding
    private val viewModel: RegisterUserFragmentVM by viewModels {
        RegisterUserFragmentVMFactory(WeakReference(context))
    }
    private var mAuth: FirebaseAuth? = null
    var fullName: String? = null
    var age: String? = null
    var email: String? = null
    var password: String? = null

    companion object {
        fun newInstance() = RegisterUserFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register_user, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        binding.btnRegisterUser.setOnClickListener {
//            var name: String = viewModel.etFullName.get().toString().trim()
//            var age: String = viewModel.etAge.get().toString().trim()
//            var email: String = viewModel.etEmail.get().toString().trim()
//            var password: String = viewModel.etPassword.get().toString().trim()
//            viewModel.validationCheck(name, age, email, password, true)
            registerUser()
        }
        binding.btnLogin.setOnClickListener {
            EventBus.getDefault().post(LoginRegisterActivity.UpdateEvents.WELCOME_LOGIN_FRAGMENT)
        }
    }

    private fun registerUser() {
        fullName = binding.etFullName.text.toString().trim()
        age = binding.etAge.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        if (fullName.isNullOrEmpty()) {
            ToastUtils.showToastShort(requireContext(), "Name is required!")
            binding.etFullName.requestFocus()
            return
        }
        if (age.isNullOrEmpty()) {
            ToastUtils.showToastShort(requireContext(), "Age is required!")
            binding.etAge.requestFocus()
            return
        }
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

        binding.progressbar.visibility = View.VISIBLE
        mAuth?.createUserWithEmailAndPassword(email!!, password!!)?.addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                val user = User(fullName!!, age!!, email!!)
                if (FirebaseAuth.getInstance().currentUser != null) {
                    FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .setValue(user)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                ToastUtils.showToastLong(
                                    requireContext(),
                                    "User has been registered successfully"
                                )
                                binding.progressbar.visibility = View.GONE
                            } else {
                                ToastUtils.showToastLong(
                                    requireContext(),
                                    "Failed to register! Try again!"
                                )
                                binding.progressbar.visibility = View.GONE

                            }
                        }
                }
            } else {
                ToastUtils.showToastLong(
                    requireContext(),
                    "Failed to register! Try again!"
                )
                binding.progressbar.visibility = View.GONE

            }
        }
    }
}