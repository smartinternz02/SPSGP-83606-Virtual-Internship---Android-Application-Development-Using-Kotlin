package com.example.groceryapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.groceryapp.R
import com.example.groceryapp.activities.GroceryActivity
import com.example.groceryapp.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signupButton.setOnClickListener {
            validateData()
        }

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        }

    }


    private fun validateData() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(activity, GroceryActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG)
                            .show()
                    }
                }

            } else {
                Snackbar.make(binding.root, "Confirm password correctly", Snackbar.LENGTH_SHORT)
                    .show()
            }
        } else {
            Snackbar.make(binding.root, "Empty Fields are not allowed", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}
