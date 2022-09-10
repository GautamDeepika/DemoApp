package com.example.demoapp.bindingadapter

import android.os.Build
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.demoapp.R
import com.example.demoapp.data.models.Priority
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.data.models.VideoModel
import com.example.demoapp.view.userprofile.fragments.list.ListFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToVideoFragment")
        @JvmStatic
        fun navigateToVideoFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_videoFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDB: MutableLiveData<Boolean>) {
            when (emptyDB.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.setSelection(0)
                Priority.MEDIUM -> view.setSelection(1)
                Priority.LOW -> view.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))
                    }
                }
                Priority.MEDIUM -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.orange))
                    }
                }
                Priority.LOW -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))
                    }
                }
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: ToDoData){
            view.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}