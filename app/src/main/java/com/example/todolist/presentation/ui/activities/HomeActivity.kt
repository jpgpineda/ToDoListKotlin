package com.example.todolist.presentation.ui.activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieDrawable
import com.example.todolist.R
import com.example.todolist.databinding.ActivityHomeBinding
import com.example.todolist.utils.FragmentCommunicator
import com.example.todolist.utils.extensions.alert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navView.setupWithNavController(navController)
    }

    override fun showLoader(isVisible: Boolean) {
        handleLoaderState(isVisible)
    }

    private fun handleLoaderState(isVisible: Boolean) {
        binding.homeLoaderContainer.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.homeAnimationView.repeatCount = LottieDrawable.INFINITE

        if (isVisible) {
            binding.homeAnimationView.playAnimation()
        } else {
            binding.homeAnimationView.cancelAnimation()
        }
    }

    override fun showMessageAlert(title: String, message: String) {

    }
}