package com.example.todolist.presentation.ui.activities.ui.addTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.data.entities.Task
import com.example.todolist.databinding.FragmentAddTaskBinding
import com.example.todolist.utils.DatePickerFragment
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.dateToTimeStamp
import com.example.todolist.utils.extensions.hideKeyboard
import com.example.todolist.utils.extensions.makeToast
import com.example.todolist.utils.extensions.shortDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date
import java.util.UUID

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.addTaskBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_addTaskFragment_to_navigation_home)
        }
        binding.saveTaskButton.setOnClickListener {
            addTask()
        }
        binding.dueDateTiet.setOnClickListener {
            showDateDialog()
        }
        binding.addTaskContainerView.setOnClickListener {
            hideKeyboard()
        }
    }
    private fun showDateDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        calendar.set(year, month, day)
        binding.dueDateTiet.setText(calendar.time.shortDate())
    }

    private fun addTask() {
        viewModel.saveNewTask(createTaskParameters()).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    makeToast(getString(R.string.task_saved_successfully))
                    findNavController().navigate(R.id.action_addTaskFragment_to_navigation_home)
                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert(getString(R.string.ups), response.message)
                }
            }
        }
    }

    private fun createTaskParameters() = Task(
        id = UUID.randomUUID().toString(),
        title = binding.titleTiet.text.toString(),
        description = binding.descriptionTiet.text.toString(),
        dueDate = calendar.time.dateToTimeStamp(),
        isCompleted = false
    )
}