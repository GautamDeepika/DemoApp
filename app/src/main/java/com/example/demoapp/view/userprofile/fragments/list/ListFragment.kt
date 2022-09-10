package com.example.demoapp.view.userprofile.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.R
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.data.viewmodel.ToDoViewModel
import com.example.demoapp.databinding.FragmentListBinding
import com.example.demoapp.view.userprofile.fragments.list.adapter.ListAdapter
import com.example.demoapp.viewmodel.userprofile.SharedViewModel
import com.example.myapplication.utils.BaseUtils.hideKeyboard
import com.example.myapplication.utils.BaseUtils.observeOnce
import com.example.myapplication.utils.ToastUtils
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private lateinit var binding: FragmentListBinding
    private val adapter: ListAdapter by lazy {
        ListAdapter(
            WeakReference(context), mutableListOf()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = sharedViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set menu
        setHasOptionsMenu(true)
        hideKeyboard(requireActivity())
        init()
    }

    private fun init() {
        binding.ivNoData.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        //observing LiveData
        toDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
            binding.recyclerView.scheduleLayoutAnimation()
        })
        swipeToDelete(recyclerView)

        // because we can observe empty database from xml file using binding adapter and sharedViewModel
        //        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
        //            showEmptyDBViews(it)
        //        })
    }

//    private fun showEmptyDBViews(emptyDB: Boolean) {
//        if (emptyDB) {
//            binding.ivNoData.visibility = View.VISIBLE
//            binding.tvNodata.visibility = View.VISIBLE
//            binding.recyclerView.visibility = View.GONE
//        } else {
//            binding.ivNoData.visibility = View.GONE
//            binding.tvNodata.visibility = View.GONE
//            binding.recyclerView.visibility = View.VISIBLE
//        }
//    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.getData()[viewHolder.adapterPosition]
                //delete item
                if (deletedItem != null) {
                    toDoViewModel.deleteItem(deletedItem)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    ToastUtils.showToastShort(
                        requireContext(),
                        "Successfully Removed: '${deletedItem.title}'"
                    )
                    //restore Deleted Item
                    restoreDeletedData(
                        viewHolder.itemView,
                        deletedItem
                    )
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: ToDoData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo") {
            toDoViewModel.insertData(deletedItem)
//            adapter.notifyItemChanged(position)
        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_priority_high -> toDoViewModel.sortByHighPriority.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                })
            R.id.menu_priority_low -> toDoViewModel.sortByLowPriority.observe(
                viewLifecycleOwner,
                Observer {
                    adapter.setData(it)
                })
        }
        return super.onOptionsItemSelected(item)
    }

    //    Show AlertDialog to confirm Removal of all items from DB table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteAll()
            ToastUtils.showToastShort(requireContext(), "Successfully Removed Everything!")
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery: String = "%$query%"
        toDoViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.setData(it)
            }

        })

    }
}