package com.example.demoapp.view.userprofile.fragments.list.adapter

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.example.demoapp.R
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.databinding.RowLayoutBinding
import com.example.demoapp.view.base.RecyclerBaseAdapter
import com.example.demoapp.view.base.RecyclerViewHolder
import com.example.demoapp.viewmodel.userprofile.RowLayoutVM
import java.lang.ref.WeakReference

class ListAdapter(
    private var weakContext: WeakReference<Context?>,
    private var items: MutableList<ToDoData>
) : RecyclerBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int) = R.layout.row_layout

    override fun getViewModel(holder: RecyclerViewHolder, position: Int): Any? {
        return if (holder.binding is RowLayoutBinding) {
            val mBinding = holder.binding as RowLayoutBinding
            val viewModel = RowLayoutVM(weakContext, holder, items[position])
            mBinding.viewModel = viewModel
            viewModel
        } else null
    }

    override fun getItemCount() = items.size

    fun getData(): List<ToDoData?> = items

//    fun setData(_todoDataItems: List<ToDoData>?) {
//        items.clear()
//        if (!_todoDataItems.isNullOrEmpty()) {
//            items.addAll(_todoDataItems)
//            val toDoDiffUtil = ToDoDiffUtil(items, _todoDataItems)
//            val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
//            //        notifyDataSetChanged()
//            //remove notifyDataSetChanged to implement DiffUtil method
//            toDoDiffResult.dispatchUpdatesTo(this)
//        }
//    }

    fun setData(_todoDataItems: List<ToDoData>?) {
        items.clear()
        if (!_todoDataItems.isNullOrEmpty())
            items.addAll(_todoDataItems)
        notifyDataSetChanged()
    }
}