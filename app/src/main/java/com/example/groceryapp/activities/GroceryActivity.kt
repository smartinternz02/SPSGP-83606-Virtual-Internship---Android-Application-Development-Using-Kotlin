package com.example.groceryapp.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groceryapp.R
import com.example.groceryapp.adapter.GroceryAdapter
import com.example.groceryapp.databinding.ActivityGroceryBinding
import com.example.groceryapp.fragments.LogInFragment
import com.example.groceryapp.repository.GroceryRepository
import com.example.groceryapp.roomdatabase.GroceryDatabase
import com.example.groceryapp.roomdatabase.GroceryItems
import com.example.groceryapp.viewModel.GroceryViewModel
import com.example.groceryapp.viewModel.GroceryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class GroceryActivity : AppCompatActivity(), GroceryAdapter.GroceryItemClickInterface {
    private lateinit var binding: ActivityGroceryBinding
    private lateinit var list: List<GroceryItems>
    private lateinit var viewModel: GroceryViewModel
    private lateinit var groceryAdapter: GroceryAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGroceryBinding.inflate(layoutInflater)
        setContentView(binding.root)





        list = listOf()
        groceryAdapter = GroceryAdapter(list, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = groceryAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]

        viewModel.getAllGroceryItems().observe(this, Observer {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()
        })

        binding.fabAdd.setOnClickListener {
            openDialog()
        }

        binding.logOut.setOnClickListener {
            Snackbar.make(binding.root, "Logged Out successfully", Snackbar.LENGTH_SHORT).show()
            supportFragmentManager.beginTransaction().add(R.id.container, LogInFragment()).commit()
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_dialog_box)
        val name = dialog.findViewById<EditText>(R.id.item_name)
        val quantity = dialog.findViewById<EditText>(R.id.item_quantity)
        val price = dialog.findViewById<EditText>(R.id.item_price)
        val cancelBtn = dialog.findViewById<Button>(R.id.cancel_button)
        val addBtn = dialog.findViewById<Button>(R.id.add_button)



        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            if (validateInput(name, quantity, price)) {
                addItemToDB(name.text.toString(), quantity.text.toString(), price.text.toString())
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun validateInput(name: TextView, quantity: TextView, price: TextView): Boolean {
        if (name.text.isEmpty()) {
            name.error = "Name is empty."
            return false
        }
        if (quantity.text.isEmpty()) {
            quantity.error = "Quantity is empty."
            return false
        }
        if (price.text.isEmpty()) {
            price.error = "Price is empty."
            return false
        }
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addItemToDB(itemName: String, itemQuantity: String, itemPrice: String) {
        val items = GroceryItems(itemName, itemQuantity.toInt(), itemPrice.toInt())
        viewModel.insert(items)
        Snackbar.make(binding.root, "Item Added", Toast.LENGTH_SHORT).show()
        groceryAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(groceryItems: GroceryItems) {
        viewModel.delete(groceryItems)
        groceryAdapter.notifyDataSetChanged()
        Snackbar.make(binding.root, "Item Deleted", Snackbar.LENGTH_SHORT).show()
    }
}