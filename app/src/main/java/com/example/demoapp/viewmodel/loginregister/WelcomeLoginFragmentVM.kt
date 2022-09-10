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

class WelcomeLoginFragmentVM(private val weakContext: WeakReference<Context?>) : ViewModel() {

    val tvWelcome = ObservableField(weakContext.get()?.getString(R.string.Welcome_txt))
    val email = ObservableField("")
    val password = ObservableField("")
    val progressbarVis = ObservableInt(View.GONE)


    override fun onCleared() {
        super.onCleared()
        weakContext.clear()
    }
}