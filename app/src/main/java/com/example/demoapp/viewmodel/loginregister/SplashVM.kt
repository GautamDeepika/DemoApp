package com.example.demoapp.viewmodel.loginregister

import android.content.Context
import android.os.Handler
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demoapp.R
import java.lang.ref.WeakReference

class SplashVM(private val weakContext: WeakReference<Context?>) : ViewModel() {

    val tvSplash = ObservableField(weakContext.get()?.getString(R.string.my_app))

    companion object {
        const val DELAY_TIME = 5000L
    }

    var startNextActivity = MutableLiveData<Boolean>(false)

    init {
        startDelay()
    }

    private fun startDelay() {
        Handler().postDelayed({ startNextActivity.apply { value = true } }, DELAY_TIME)
    }

    override fun onCleared() {
        super.onCleared()
        weakContext.clear()
    }
}