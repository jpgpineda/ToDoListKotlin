package com.example.todolist.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.todolist.R
import com.example.todolist.databinding.FragmentRegisterBinding
import com.example.todolist.utils.DatePickerFragment
import com.google.android.gms.cast.framework.media.ImagePicker

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
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
        setupView()
        return binding.root
    }

    private fun setupView() {
        pickImageView = binding.profileImage
        binding.profileImage.setOnClickListener {
            pickerMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.bornDateTiet.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        binding.bornDateTiet.setText("$day/$month/$year")
    }
}