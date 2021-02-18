package com.collegeapp.advertmarketapp.screens.auth.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.collegeapp.advertmarketapp.App
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.database.AppDatabase
import com.collegeapp.advertmarketapp.database.dao.UsersDAO
import com.collegeapp.advertmarketapp.database.entity.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var usersDAO: UsersDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.view_login, container, false)

        db = (requireContext().applicationContext as App).getDatabase()
        usersDAO = db.usersDAO()

        val login = root.findViewById<EditText>(R.id.login_login)
        val pass = root.findViewById<EditText>(R.id.password_login)
        val loginBtn = root.findViewById<Button>(R.id.login_in_btn)

        loginBtn.setOnClickListener {

            if (login.text.isNotEmpty() && pass.text.isNotEmpty()) {

                CoroutineScope(Dispatchers.IO).launch {
                    val user = usersDAO.getByLogin(login.text.toString())

                    if (user != null) {
                        checkPass(user, pass)
                    } else {
                        login.setTextColor(Color.RED)
                    }

                }

            }

        }

        return root
    }

    private suspend fun checkPass(user: Users, pass: EditText) {
        withContext(Dispatchers.Main) {
            if (user.password == pass.text.toString()) {

                user.isLogin = true

                usersDAO.update(user)
                findNavController().navigate(R.id.to_home)
            } else {
                pass.setTextColor(Color.RED)
            }
        }
    }

}