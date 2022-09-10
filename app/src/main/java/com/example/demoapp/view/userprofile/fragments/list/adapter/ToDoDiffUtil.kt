package com.example.demoapp.view.userprofile.fragments.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.demoapp.data.models.ToDoData

class ToDoDiffUtil(
    private val oldList: List<ToDoData>?,
    private val newList: List<ToDoData>?
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldList != null && newList != null) oldList[oldItemPosition] == newList[newItemPosition] else false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (oldList != null && newList != null) oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].description == newList[newItemPosition].description
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority else false
    }
}