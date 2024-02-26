package com.example.todolist.presentation.ui.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.entities.Task
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.utils.FragmentCommunicator
import java.util.Date

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var communicator: FragmentCommunicator
    private lateinit var adapter: TaskAdapter
    private val viewModel by viewModels<HomeViewModel>()

    val taskList = mutableListOf<Task>(
        Task("1",
            "Tarea 1",
            "Tarea 1",
            Date(),
            isCompleted = true
        ),
        Task("1",
            "Tarea 1",
            "Tarea 1",
            Date(),
            isCompleted = true
        ),
        Task("1",
            "Tarea 1",
            "Tarea 1",
            Date(),
            isCompleted = true
        ),
        Task("1",
            "Tarea 1",
            "Tarea 1",
            Date(),
            isCompleted = true
        ),
        Task("1",
            "Tarea 1",
            "Tarea 1",
            Date(),
            isCompleted = true
        )
    )

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
        adapter = TaskAdapter(taskList)
        binding.taskRecyclerView.adapter = adapter
    }

}