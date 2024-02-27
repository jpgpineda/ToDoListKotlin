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
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<AddTaskViewModel>()
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
            findNavController().navigate(AddTaskFragmentDirections.actionAddTaskFragmentToNavigationHome(taskId = "1232dde2fdewf2" ,isTaskSaved = true))
        }
        binding.saveTaskButton.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        viewModel.saveNewTask(createTaskParameters()).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {

                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert(getString(R.string.ups), response.message)
                }
            }
        }
    }

    private fun createTaskParameters() = Task(
        id = "",
        title = binding.titleTiet.text.toString(),
        description = binding.descriptionTiet.text.toString(),
        dueDate = Date(),
        isCompleted = false
    )
}