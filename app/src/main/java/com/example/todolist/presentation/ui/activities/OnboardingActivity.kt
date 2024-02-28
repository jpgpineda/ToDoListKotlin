package com.example.todolist.presentation.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.todolist.R
import com.example.todolist.databinding.ActivityOnboardingBinding
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.extensions.alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity(), FragmentCommunicator {
    private lateinit var binding: ActivityOnboardingBinding

    companion object {
        lateinit var appContext: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = this
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showLoader(isVisible: Boolean) {
        handleLoaderState(isVisible)
    }

    private fun handleLoaderState(isVisible: Boolean) {
        binding.loaderContentView.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.loadingAnimationView.repeatCount = LottieDrawable.INFINITE

        if (isVisible) {
            binding.loadingAnimationView.playAnimation()
        } else {
            binding.loadingAnimationView.cancelAnimation()
        }
    }

    override fun showMessageAlert(title: String, message: String) {
        alert {
            setTitle(title)
            setMessage(message)
        }
    }
}