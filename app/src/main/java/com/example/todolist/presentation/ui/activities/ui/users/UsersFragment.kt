package com.example.todolist.presentation.ui.activities.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.databinding.FragmentUsersBinding
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.snack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding
    private val viewModel by viewModels<UsersViewModel>()
    private lateinit var adapter: CharacterAdapter
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupView()
        bind()
        return binding.root
    }

    private fun setupView() {
        binding.RvCharacters.layoutManager = LinearLayoutManager(requireActivity())
        adapter = CharacterAdapter(mutableListOf())
        binding.RvCharacters.adapter = adapter
    }

    private fun bind() {
        viewModel.getCharacters().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    response.data.body()?.results?.let { viewModel.setData(it) }
                    adapter.clearData()
                    viewModel.charactersList.value?.let { tasks ->
                        adapter.add(tasks)
                    }
                }
                is Response.Failure -> {
                    snack(response.message)
                }
            }
        }
    }
}