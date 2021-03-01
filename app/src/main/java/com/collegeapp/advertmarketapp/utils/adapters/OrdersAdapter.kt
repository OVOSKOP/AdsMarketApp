package com.collegeapp.advertmarketapp.utils.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.entity.Order
import com.collegeapp.advertmarketapp.utils.holders.OrderHolder

class OrdersAdapter(private val context: Context) : RecyclerView.Adapter<OrderHolder>() {

    private var items: List<Order> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        return OrderHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_order, parent, false), context)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun refresh(items: List<Order>) {
        this.items = items
        notifyDataSetChanged()
    }
}