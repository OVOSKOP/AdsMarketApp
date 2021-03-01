package com.collegeapp.advertmarketapp.utils.holders

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.collegeapp.advertmarketapp.App
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.entity.Order
import com.collegeapp.advertmarketapp.utils.views.StatusView
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class OrderHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    private var title: TextView = itemView.findViewById(R.id.title_order)
    private var type: TextView = itemView.findViewById(R.id.type_adv)
    private var statusTxt: TextView = itemView.findViewById(R.id.status_txt)
    private var statusView: StatusView = itemView.findViewById(R.id.status_view)
    private var work: Button = itemView.findViewById(R.id.work_up)
    private var date: TextView = itemView.findViewById(R.id.date_added)

    fun onBind(order: Order) {
        title.text = order.title
        statusTxt.text = "Этап разработки: ${context.resources.getStringArray(R.array.status)[order.status]}"
        statusView.status = order.status
        type.text = "${context.resources.getStringArray(R.array.types)[order.type]}"

        val dateAdded = Date(order.date)
        val format = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        date.text = format.format(dateAdded)

        if ((context.applicationContext as App).currentUser!!.role - 1 != order.status) {
            work.text = "Просмотр"
        }

        work.setOnClickListener {
            val navController = Navigation.findNavController(itemView)

            val args = Bundle()
            args.putInt("orderId", order.id)

            navController.navigate(R.id.to_order, args)
        }
    }

}