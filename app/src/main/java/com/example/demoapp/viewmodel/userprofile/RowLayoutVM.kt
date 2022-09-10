package com.example.demoapp.viewmodel.userprofile

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.demoapp.R
import com.example.demoapp.data.models.Priority
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.view.base.RecyclerViewHolder
import com.example.demoapp.view.userprofile.fragments.list.ListFragmentDirections
import java.lang.ref.WeakReference

class RowLayoutVM(
    private var weakContext: WeakReference<Context?>,
    private val holder: RecyclerViewHolder,
    private var listItem: ToDoData
) {

    fun cardBackgroundColor() = listItem.priority

//    fun cardBackgroundColor(): Int {
//        return ContextCompat.getColor(weakContext.get()!!, setPriorityColor())
//    }

//    private fun setPriorityColor(): Int {
//        return when (listItem.priority) {
//            Priority.HIGH -> R.color.red
//            Priority.MEDIUM -> R.color.orange
//            Priority.LOW -> R.color.green
//            else -> R.color.black
//        }
//    }

    fun getTitle() = listItem.title
    fun getDescription() = listItem.description

    fun getSendDataToUpdateFragment() = listItem

//    fun onItemClicked(view: View) {
//        val action =
//            ListFragmentDirections.actionListFragmentToUpdateFragment(listItem)
//        holder.itemView.findNavController().navigate(action)
//    }
}