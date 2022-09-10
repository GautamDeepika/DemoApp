package com.example.demoapp.viewmodel.loginregister

import android.content.Context
import android.os.Handler
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.R
import com.example.myapplication.utils.BaseUtils
import com.example.myapplication.utils.ToastUtils
import java.lang.ref.WeakReference

class RegisterUserFragmentVM(private val weakContext: WeakReference<Context?>) : ViewModel() {

    val tvWelcome = ObservableField(weakContext.get()?.getString(R.string.my_app))
    val etFullName = ObservableField("")
    val etAge = ObservableField("")
    val etEmail = ObservableField("")
    val etPassword = ObservableField("")
    val progressbarVis = ObservableInt(View.GONE)

    fun validationCheck(
        name: String,
        age: String,
        email: String,
        password: String,
        showToast: Boolean = false
    ): Boolean {
        return if (name.isNullOrEmpty()) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_enter_full_name)
            )
            false
        } else if (age.isNullOrEmpty()) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_enter_age)
            )
            false
        } else if (email.isNullOrEmpty()) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_enter_valid_email)
            )
            false
        } else if (!isValidEmail(email)) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_enter_email)
            )
            false
        } else if (password.isNullOrEmpty()) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_enter_password)
            )
            false
        } else if (weakContext.get() == null || !BaseUtils.isInternetConnected(weakContext.get()!!)) {
            if (showToast) ToastUtils.showToastShort(
                weakContext.get(),
                weakContext.get()!!.getString(R.string.please_check_your_internet)
            )
            false
        } else true
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !target.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    override fun onCleared() {
        super.onCleared()
        weakContext.clear()
    }
}