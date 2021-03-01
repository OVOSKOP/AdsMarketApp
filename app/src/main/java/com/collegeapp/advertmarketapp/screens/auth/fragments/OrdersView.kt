package com.collegeapp.advertmarketapp.screens.auth.fragments

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.dao.ClientDAO
import com.collegeapp.advertmarketapp.database.dao.OrderDAO
import com.collegeapp.advertmarketapp.utils.ProfessionCode
import com.collegeapp.advertmarketapp.utils.adapters.OrdersAdapter
import com.collegeapp.advertmarketapp.utils.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var db: AppDatabase? = null
    private var orderDAO: OrderDAO? = null

    private val list: RecyclerView

    private val adapter: OrdersAdapter

    init {
        val root = inflate(context, R.layout.content_orders, this)

        if (!isInEditMode) {
            db = app.getDatabase()
            orderDAO = db?.orderDAO()
        }

        list = root.findViewById(R.id.orders_list)
        adapter = OrdersAdapter(context)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(list.context,
                layoutManager.orientation)

        list.adapter = adapter
        list.layoutManager = layoutManager
        list.addItemDecoration(dividerItemDecoration)

        val role = app.currentUser?.role
        role?.apply {
            showOrders(role)
        }
    }

    private fun showOrders(role: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val list = if (role == ProfessionCode.DIRECTOR) {
                orderDAO?.getList()
            } else {
                orderDAO?.getByStatus(role - 1)
            }

            list?.apply {
                adapter.refresh(list)
            }
        }
    }

}