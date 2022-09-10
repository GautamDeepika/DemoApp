package com.example.demoapp.viewmodel.loginregister

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.R
import java.lang.ref.WeakReference

class LoginRegisterVM(private val weakContext: WeakReference<Context?>) : ViewModel() {

    val tvWelcome = ObservableField(weakContext.get()?.getString(R.string.my_app))
    val etFullName = ObservableField("")
    val etAge = ObservableField("")
    val etEmail = ObservableField("")
    val etPassword = ObservableField("")
    val progressbarVis = ObservableInt(View.GONE)


    override fun onCleared() {
        super.onCleared()
        weakContext.clear()
    }
}