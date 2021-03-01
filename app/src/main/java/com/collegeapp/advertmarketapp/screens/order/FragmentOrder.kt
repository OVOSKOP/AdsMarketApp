package com.collegeapp.advertmarketapp.screens.order

import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.utils.CHOOSE_FILE
import com.collegeapp.advertmarketapp.utils.ProfessionCode
import com.collegeapp.advertmarketapp.utils.app
import com.collegeapp.advertmarketapp.utils.views.AddOrdersView
import com.collegeapp.advertmarketapp.utils.views.OrderView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI


class FragmentOrder : Fragment() {

    private lateinit var orderView: OrderView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        val user = app.currentUser

        val db = app.getDatabase()
        val orderDAO = db.orderDAO()

        val frame = root.findViewById<FrameLayout>(R.id.frame_order)

        val toolbar: Toolbar = root.findViewById(R.id.toolbar_order)

        toolbar.navigationIcon = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.ic_baseline_arrow_back_ios_new_24
        )
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val id = arguments?.getInt("orderId")

        CoroutineScope(Dispatchers.IO).launch {
            val order = orderDAO.getById(id ?: 0)

            withContext(Dispatchers.Main) {
                user?.apply {
                    if (user.role == ProfessionCode.CLIENT_MANAGER) {
                        toolbar.title = "Добавить заказ"
                        frame.addView(AddOrdersView(requireContext(), null))
                    } else {
                        toolbar.title = "Работа над заказом"
                        orderView = OrderView(requireContext(), order, null)
                        frame.addView(orderView)
                    }
                }
            }

        }

        return root
    }

    fun setUrls(url: Uri?, code: Int) {
        orderView.setUrl(url, code)
    }

}