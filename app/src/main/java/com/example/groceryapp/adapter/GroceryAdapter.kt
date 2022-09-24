package com.example.groceryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.roomdatabase.GroceryItems

class GroceryAdapter(
    var list: List<GroceryItems>,
    private val groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        val quantity: TextView = view.findViewById(R.id.item_quantity)
        val price: TextView = view.findViewById(R.id.item_price)
        val delete: ImageView = view.findViewById(R.id.delete)
        val totalCost: TextView = view.findViewById(R.id.total_cost_price)
    }

    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {

        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.grocery_list_item, parent, false)
        return GroceryViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.itemName
        holder.quantity.text = "x" + item.itemQuantity.toString()
        holder.price.text = "Rs. " + item.itemPrice.toString()

        val totalAmount: Int = item.itemQuantity * item.itemPrice
        holder.totalCost.text = "Rs. " + totalAmount.toString()
        holder.delete.setOnClickListener {
            groceryItemClickInterface.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = list.size
}