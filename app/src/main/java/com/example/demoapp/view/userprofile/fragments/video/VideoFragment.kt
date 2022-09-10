package com.example.demoapp.view.userprofile.fragments.video

import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.demoapp.R
import com.example.demoapp.data.models.VideoModel
import com.example.demoapp.databinding.FragmentVideoBinding
import com.example.demoapp.view.userprofile.fragments.video.adapter.VideoListAdapter
import com.example.demoapp.viewmodel.userprofile.VideoFragmentVM
import com.example.demoapp.viewmodel.userprofile.VideoFragmentVMFactory
import java.lang.ref.WeakReference


class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding
    private val viewModel: VideoFragmentVM by viewModels {
        VideoFragmentVMFactory(WeakReference(context))
    }

    private val videoList: ArrayList<VideoModel> = ArrayList()

    private lateinit var adapter: VideoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateArrayList()
        init()
    }

    private fun populateArrayList() {
        videoList.add(
            VideoModel(
                "Big Buck Bunny",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            )
        )
        videoList.add(
            VideoModel(
                "We are going on bull run",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
            )
        )
        videoList.add(
            VideoModel(
                "Volkswagen GTI Review",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4"
            )
        )
        videoList.add(
            VideoModel(
                "For Bigger Blazes",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
            )
        )
        videoList.add(
            VideoModel(
                "Subaru Outback On Street And Dirt",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
            )
        )
        videoList.add(
            VideoModel(
                "What care can you get for ten grand?",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4"
            )
        )
    }

    private fun init() {
        adapter = VideoListAdapter(WeakReference(context), videoList)
        val recyclerView = binding.videoRecycler
        recyclerView.adapter = adapter
    }
}