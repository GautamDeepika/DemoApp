package com.example.demoapp.view.userprofile.fragments.video.adapter

import android.content.Context
import com.example.demoapp.R
import com.example.demoapp.data.models.VideoModel
import com.example.demoapp.databinding.ItemVideoLayoutBinding
import com.example.demoapp.view.base.RecyclerBaseAdapter
import com.example.demoapp.view.base.RecyclerViewHolder
import com.example.demoapp.viewmodel.userprofile.ItemVideoLayoutVM
import java.lang.ref.WeakReference

class VideoListAdapter(
    private val weakContext: WeakReference<Context?>,
    private val videoListItems: ArrayList<VideoModel>
) : RecyclerBaseAdapter() {
    override fun getLayoutIdForPosition(position: Int) = R.layout.item_video_layout

    override fun getViewModel(holder: RecyclerViewHolder, position: Int): Any? {
        return if (holder.binding is ItemVideoLayoutBinding) {
            val mBinding = holder.binding as ItemVideoLayoutBinding
            val viewModel = ItemVideoLayoutVM(weakContext, videoListItems[position])
            mBinding.viewModel = viewModel
            viewModel
        } else null
    }

    override fun getItemCount() = videoListItems.size
}