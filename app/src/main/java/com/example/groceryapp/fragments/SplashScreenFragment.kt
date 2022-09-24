package com.example.groceryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.groceryapp.R
import com.example.groceryapp.activities.GroceryActivity
import com.example.groceryapp.databinding.FragmentSplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()

        Handler().postDelayed(
            {
                findNavController().navigate(R.id.action_splashScreenFragment_to_logInFragment)
            },
            2000
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(context, GroceryActivity::class.java)
            startActivity(intent)
        }
    }
}