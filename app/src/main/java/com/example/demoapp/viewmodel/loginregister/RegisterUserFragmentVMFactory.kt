package com.example.demoapp.viewmodel.loginregister

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.ref.WeakReference

class RegisterUserFragmentVMFactory(private val weakContext: WeakReference<Context?>) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterUserFragmentVM(weakContext) as T
    }
}