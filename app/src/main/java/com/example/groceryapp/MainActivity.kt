package com.example.groceryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.groceryapp.databinding.ActivityMainBinding
import com.example.groceryapp.interfac.Navigation

class MainActivity : AppCompatActivity(), Navigation {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.frame,LogInFragment()).commit()

    }

    override fun nav(fragment: Fragment, addtoStack: Boolean) {
        val nav1 = supportFragmentManager.beginTransaction().replace(R.id.frame,fragment)
        if(addtoStack) {
            nav1.addToBackStack(null)
        }
        else {
            nav1.commit()
        }
    }
}