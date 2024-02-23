package com.example.todolist.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.todolist.data.entities.RegisterRequest
import com.example.todolist.databinding.FragmentRegisterBinding
import com.example.todolist.presentation.ui.viewModels.RegisterViewModel
import com.example.todolist.utils.DatePickerFragment
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var communicator: FragmentCommunicator
    lateinit var pickImageView: ImageView
    private val pickerMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { media ->
        if (media!=null) {
            pickImageView.setImageURI(media)
        } else {
            Log.i("Error", "No seleciono nada")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        communicator = requireActivity() as FragmentCommunicator
        setupView()
        return binding.root
    }

    private fun setupView() {
        pickImageView = binding.profileImage
        binding.profileImage.setOnClickListener {
            pickerMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.bornDateTiet.setOnClickListener { view ->
            showDialog()
        }
        binding.emailTiet.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.emailTil.helperText = validEmail()
            } else {
                binding.emailTil.helperText = "Obligatorio"
            }
        }
        binding.passwordTiet.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordTil.helperText = validPassword()
            } else {
                binding.passwordTil.helperText = "obligatorio"
            }
        }
        binding.signUpButton.setOnClickListener {
            requestSignUp()
        }
    }

    private fun requestSignUp() {
        viewModel.register(createParameters()).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Felicidades", "Tu cuenta se ha creado")
                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Ups!", response.message)
                }
            }
        }
    }

    private fun createParameters() = RegisterRequest(
        binding.emailTiet.text.toString(),
        binding.lastNameTiet.text.toString(),
        binding.userNameTiet.text.toString(),
        binding.bornDateTiet.text.toString(),
        binding.emailTiet.text.toString(),
        binding.passwordTiet.text.toString()
    )

    private fun validPassword(): CharSequence? {
        val passswordText = binding.passwordTiet.text.toString()
        if (passswordText.length < 10) {
            return "Caracteres minimos de 8"
        }
        if (!passswordText.matches(".*[A-Z].*".toRegex())) {
            return "Debe tener una letra mayuscula"
        }
        if (!passswordText.matches(".*[@#/$%&=+*].*".toRegex())) {
            return "Debe tener un caracter especial"
        }
        if (!passswordText.matches(".*[a-z].*".toRegex())) {
            return "Debe tener una letra minuscula"
        }
        return null
    }

    private fun validEmail(): String? {
        val emailText = binding.emailTiet.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Ingresa un email valido"
        }
        return null
    }

    private fun showDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.bornDateTiet.setText("$day/$month/$year")
    }
}