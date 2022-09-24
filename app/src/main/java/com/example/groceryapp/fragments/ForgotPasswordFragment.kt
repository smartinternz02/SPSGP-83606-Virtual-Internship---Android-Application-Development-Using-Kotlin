package com.example.groceryapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.groceryapp.R
import com.example.groceryapp.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.submitButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (email.isNotEmpty()) {

                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_logInFragment)
                        Toast.makeText(
                            requireContext(),
                            "Email sent successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            task.exception!!.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            } else if (email.isEmpty()) {
                Snackbar.make(binding.root, "Please enter email", Snackbar.LENGTH_SHORT).show()

            }
        }

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_logInFragment)
        }
    }
}