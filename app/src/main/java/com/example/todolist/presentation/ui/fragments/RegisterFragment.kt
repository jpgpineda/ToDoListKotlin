package com.example.todolist.presentation.ui.fragments

import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.data.request.LoginRequest
import com.example.todolist.data.request.PersonalInfoRequest
import com.example.todolist.data.request.RegisterRequest
import com.example.todolist.databinding.FragmentRegisterBinding
import com.example.todolist.presentation.ui.activities.HomeActivity
import com.example.todolist.presentation.ui.viewModels.RegisterViewModel
import com.example.todolist.utils.DatePickerFragment
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.Response
import com.example.todolist.utils.extensions.dateToTimeStamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var communicator: FragmentCommunicator
    lateinit var pickImageView: ImageView
    val calendar = Calendar.getInstance()
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
        binding.registerBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
        }
    }

    private fun requestSignUp() {
        val parameters = createParameters()
        viewModel.register(parameters).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Felicidades", "Tu cuenta se ha creado")
                    requestLogin(parameters = LoginRequest(parameters.email, parameters.password))
                }
                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Ups!", response.message)
                }
            }
        }
    }

    fun requestLogin(parameters: LoginRequest) {
        viewModel.login(parameters).observe(viewLifecycleOwner) { response ->
            when(response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }

                is Response.Success -> {
                    communicator.showLoader(false)
                    savePersonalInfo()
                }

                is Response.Failure -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Ups!", response.message)
                }
            }
        }
    }

    private fun savePersonalInfo() {
        viewModel.savePersonalInfo(createPersonalRequest()).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Loading -> {
                    communicator.showLoader(true)
                }
                is Response.Success -> {
                    communicator.showLoader(false)
                    communicator.showMessageAlert("Felicidades", "Tu informacion se ha guardado correctamente")
                    val intent = Intent(activity, HomeActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
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

    private fun createPersonalRequest() = PersonalInfoRequest(
        binding.nameTiet.text.toString(),
        binding.lastNameTiet.text.toString(),
        binding.userNameTiet.text.toString(),
        calendar.time.dateToTimeStamp(),
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
        calendar.set(year, month, day)
        binding.bornDateTiet.setText("$day/$month/$year")
    }
}