package com.example.demoapp.viewmodel.userprofile

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import com.example.demoapp.R
import com.example.demoapp.data.models.Priority
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.data.models.VideoModel
import com.example.demoapp.view.base.RecyclerViewHolder
import com.example.demoapp.view.userprofile.fragments.list.ListFragmentDirections
import com.example.demoapp.view.userprofile.fragments.video.VideoViewActivity
import java.lang.ref.WeakReference

class ItemVideoLayoutVM(
    private var weakContext: WeakReference<Context?>,
    private var listItem: VideoModel
) {

    fun getTitle() = listItem.name


    fun onItemClicked(view: View) {
        if (weakContext.get() == null) return
        val intent = Intent(weakContext.get()!!, VideoViewActivity::class.java)
        intent.putExtra("videoUrl", listItem.videoUrl)
        weakContext.get()!!.startActivity(intent)
    }

}