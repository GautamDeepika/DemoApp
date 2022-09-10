package com.example.demoapp.view.userprofile.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.demoapp.R
import com.example.demoapp.data.models.ToDoData
import com.example.demoapp.data.viewmodel.ToDoViewModel
import com.example.demoapp.databinding.FragmentUpdateBinding
import com.example.demoapp.viewmodel.userprofile.SharedViewModel
import com.example.myapplication.utils.BaseUtils.hideKeyboard
import com.example.myapplication.utils.ToastUtils


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    private lateinit var binding: FragmentUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //DataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        binding.args = args.currentItem
        //Set Menu
        setHasOptionsMenu(true)

        binding.etTitleUpdate.setText(args.currentItem.title)
        binding.etDescriptionUpdate.setText(args.currentItem.description)
        binding.spinnerPriorityUpdate.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.spinnerPriorityUpdate.onItemSelectedListener =
            mSharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        hideKeyboard(requireActivity())
        val titleToBeUpdated = binding.etTitleUpdate.text.toString()
        val descriptionToBeUpdated = binding.etDescriptionUpdate.text.toString()
        val getPriority = binding.spinnerPriorityUpdate.selectedItem.toString()
        val validation =
            mSharedViewModel.verifyDataFromUser(titleToBeUpdated, descriptionToBeUpdated)
        if (validation) {
            //update current item
            val updatedItem = ToDoData(
                args.currentItem.id,
                titleToBeUpdated,
                mSharedViewModel.parsePriority(getPriority),
                descriptionToBeUpdated
            )

            toDoViewModel.updateData(updatedItem)
            ToastUtils.showToastShort(requireContext(), "Successfully updated!")
            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            ToastUtils.showToastShort(requireContext(), "Please fill out all fields.")
        }
    }

    //show AlertDialog to confirm item Removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            toDoViewModel.deleteItem(args.currentItem)
            ToastUtils.showToastShort(
                requireContext(), "Successfully Removed: ${args.currentItem.title}")
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

}