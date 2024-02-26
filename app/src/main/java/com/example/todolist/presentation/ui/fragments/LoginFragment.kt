package com.example.todolist.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.data.request.LoginRequest
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.presentation.ui.activities.HomeActivity
import com.example.todolist.presentation.ui.viewModels.LoginViewModel
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var communicator: FragmentCommunicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
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
        binding.loginButton.setOnClickListener {
            requestLogin()
        }
    }

    private fun requestLogin() {
        viewModel.login(createLoginParameters()).observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert(getString(R.string.ups), response.message)
                }
            }

        }
    }

    private fun createLoginParameters() = LoginRequest(
        binding.loginEmailTiet.text.toString(),
        binding.lognPasswordTiet.text.toString()
    )
}