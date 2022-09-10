package com.example.demoapp.view.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    var binding: ViewDataBinding? = null
    init {
        bind()
    }

     fun bind() {
        if (binding == null) binding = DataBindingUtil.bind(itemView)
    }

    fun unbind(){
       binding?.unbind()
    }
}