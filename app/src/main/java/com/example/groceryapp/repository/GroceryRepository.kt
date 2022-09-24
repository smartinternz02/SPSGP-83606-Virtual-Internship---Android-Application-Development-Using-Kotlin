package com.example.groceryapp.repository

import com.example.groceryapp.roomdatabase.GroceryDatabase
import com.example.groceryapp.roomdatabase.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(item: GroceryItems) = db.getGroceryDao().insert(item)
    suspend fun delete(item: GroceryItems) = db.getGroceryDao().delete(item)
    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()
}