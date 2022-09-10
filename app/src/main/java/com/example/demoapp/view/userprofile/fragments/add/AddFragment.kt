package com.example.demoapp.view.userprofile.fragments.add

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.fragment.findNavController
import com.example.demoapp.R
import com.example.demoapp.data.models.Priority
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.data.viewmodel.ToDoViewModel
import com.example.demoapp.databinding.FragmentAddBinding
import com.example.demoapp.viewmodel.userprofile.SharedViewModel
import com.example.myapplication.utils.BaseUtils.hideKeyboard
import com.example.myapplication.utils.ToastUtils

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    private val toDoViewModel: ToDoViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        binding.spinnerPriority.onItemSelectedListener = sharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDB()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {
        hideKeyboard(requireActivity())
        val mTitle = binding.etTitle.text.toString()
        val mPriority =
            binding.spinnerPriority.selectedItem.toString()
        val mDescription = binding.etDescription.text.toString()

        val validation = sharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            //insert data to database
            val newData = ToDoData(
                0,
                mTitle,
                sharedViewModel.parsePriority(mPriority),
                mDescription
            )
            toDoViewModel.insertData(newData)
            ToastUtils.showToastShort(requireContext(), "Successfully added!")

            //navigate back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }
}

