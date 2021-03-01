package com.collegeapp.advertmarketapp.screens.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.collegeapp.advertmarketapp.App
import com.collegeapp.advertmarketapp.MainActivity
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.dao.UsersDAO
import com.collegeapp.advertmarketapp.database.entity.Users
import com.collegeapp.advertmarketapp.screens.auth.fragments.OrdersView
import com.collegeapp.advertmarketapp.utils.ProfessionCode
import com.collegeapp.advertmarketapp.utils.app
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentMain : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var usersDAO: UsersDAO

    private lateinit var quit: View
    private lateinit var root: View
    private lateinit var frameLayout: FrameLayout

    private lateinit var add: Button

    private lateinit var navController: NavController
    private var user: Users? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_main, container, false)
        frameLayout = root.findViewById(R.id.frame)

        user = app.currentUser

        if (user!!.role == ProfessionCode.CLIENT_MANAGER) {
            inflater.inflate(R.layout.content_add_order, frameLayout, true)

            add = root.findViewById(R.id.add_order)

            add.setOnClickListener {
                findNavController().navigate(R.id.to_order)
            }
        } else {
            frameLayout.addView(OrdersView(requireContext(), null))
        }

        db = app.getDatabase()
        usersDAO = db.usersDAO()

        val toolbar: Toolbar = root.findViewById(R.id.toolbar)
        root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = "Юга+"

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        quit = root.findViewById(R.id.quit)
        quit.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if (user != null) {
                    user!!.isLogin = false

                    usersDAO.update(user!!)
                }
            }

            requireActivity().moveTaskToBack(true)
            requireActivity().finish()
        }

        if (user != null) {
            val profileLabel = root.findViewById<TextView>(R.id.profile_txt)
            val statusTxt = root.findViewById<TextView>(R.id.user_status)
            statusTxt.visibility = View.VISIBLE
            profileLabel.text = "${user?.name} (${user?.login})"
            println(user!!.role)
            val roles = resources.getStringArray(R.array.roles)

            statusTxt.text = roles[user!!.role]
        }

        return root
    }

}