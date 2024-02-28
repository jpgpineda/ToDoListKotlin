package com.example.todolist.presentation.ui.activities.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.entities.Task
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.makeToast
import com.example.todolist.utils.extensions.snack
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var communicator: FragmentCommunicator
    private lateinit var adapter: TaskAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = TaskAdapter(mutableListOf())
        binding.taskRecyclerView.adapter = adapter
        binding.addTaskButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_addTaskFragment)
        }
        bind()
    }

    private fun bind() {
        viewModel.getTask().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    response.data?.let { viewModel.setTaskData(it) }
                    adapter.clearRecycler()
                    viewModel.getTaskList.value?.let { tasks ->
                        adapter.add(tasks)
                    }
                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    makeToast(response.message, Toast.LENGTH_LONG)
                }
            }
        }
    }
}