package com.example.demoapp.viewmodel.userprofile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.ref.WeakReference

class VideoFragmentVMFactory(val weakContext: WeakReference<Context?>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoFragmentVM(weakContext) as T
    }
}