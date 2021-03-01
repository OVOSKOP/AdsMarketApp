package com.collegeapp.advertmarketapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.navigation.findNavController
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.dao.ClientDAO
import com.collegeapp.advertmarketapp.database.dao.OrderDAO
import com.collegeapp.advertmarketapp.database.dao.UsersDAO
import com.collegeapp.advertmarketapp.database.entity.Client
import com.collegeapp.advertmarketapp.database.entity.Order
import com.collegeapp.advertmarketapp.utils.app
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class AddOrdersView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var db: AppDatabase? = null
    private var clientDAO: ClientDAO? = null
    private var orderDAO: OrderDAO? = null

    private val clientName: EditText
    private val clientEmail: EditText
    private val clientPhone: EditText

    private val orderTitle: EditText
    private val orderType: Spinner
    private val orderArea: EditText
    private val orderDesc: EditText

    private val save: Button

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.content_add_orders, this, true)

        if (!isInEditMode) {
            db = app.getDatabase()
            clientDAO = db?.clientDAO()
            orderDAO = db?.orderDAO()
        }

        clientName = root.findViewById(R.id.client_name)
        clientEmail = root.findViewById(R.id.client_email)
        clientPhone = root.findViewById(R.id.client_phone)

        orderTitle = root.findViewById(R.id.order_title)
        orderType = root.findViewById(R.id.order_type)
        orderArea = root.findViewById(R.id.order_area)
        orderDesc = root.findViewById(R.id.order_client)

        save = root.findViewById(R.id.save)

        save.setOnClickListener {
            addOrder()
        }
    }

    private fun addOrder() {
        CoroutineScope(Dispatchers.IO).launch {
            val client = Client(
                clientName.text.toString(),
                clientEmail.text.toString(),
                clientPhone.text.toString()
            )

            val id = clientDAO?.insert(client)

            val order = Order(
                orderTitle.text.toString(),
                id!!,
                orderType.selectedItemPosition,
                orderArea.text.toString(),
                orderDesc.text.toString(),
                Date().time
            )

            orderDAO?.insert(order)
            withContext(Dispatchers.Main) {
                findNavController().popBackStack()
            }
        }
    }

}