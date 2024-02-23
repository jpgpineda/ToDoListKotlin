package com.example.todolist.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.utils.extensions.hideKeyboard

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        binding.loginBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
        binding.loginMainContainer.setOnClickListener {
            hideKeyboard()
        }
    }
}