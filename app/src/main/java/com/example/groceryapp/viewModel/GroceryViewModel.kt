package com.example.groceryapp.viewModel

import androidx.lifecycle.ViewModel
import com.example.groceryapp.repository.GroceryRepository
import com.example.groceryapp.roomdatabase.GroceryItems
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {

    fun insert(item: GroceryItems) = GlobalScope.launch {
        repository.insert(item)
    }

    fun delete(item: GroceryItems) = GlobalScope.launch {
        repository.delete(item)
    }

    fun getAllGroceryItems() = repository.getAllItems()
}

