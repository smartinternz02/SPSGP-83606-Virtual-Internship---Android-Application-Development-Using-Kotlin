package com.example.groceryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.groceryapp.databinding.FragmentSignUpBinding
import com.example.groceryapp.interfac.Navigation
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var binding : FragmentSignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding  = FragmentSignUpBinding.inflate(layoutInflater)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener {
            var email = binding.emailEditText.text.toString()
            var password = binding.passwordEditText.text.toString()

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->

                if (task.isSuccessful){
                    var navR = activity as Navigation
                    navR.nav(HomeFragment(),true)
                }else{
                    Toast.makeText(requireContext(),task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
        return (binding.root)
    }


}