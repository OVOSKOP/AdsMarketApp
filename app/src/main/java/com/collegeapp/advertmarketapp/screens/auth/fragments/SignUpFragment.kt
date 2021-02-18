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
import com.collegeapp.advertmarketapp.database.entity.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.view_signup, container, false)

        val db = (requireContext().applicationContext as App).getDatabase()
        val usersDAO = db.usersDAO()

        val nameTxt = root.findViewById<EditText>(R.id.name)
        val loginTxt = root.findViewById<EditText>(R.id.login_sign_in)
        val pass = root.findViewById<EditText>(R.id.password_sign)
        val secondPass = root.findViewById<EditText>(R.id.confirm_pass_sign)

        val sign = root.findViewById<Button>(R.id.sign_in_btn)

        sign.setOnClickListener {
            if (pass.text.toString() == secondPass.text.toString()) {
                val user = Users(
                        nameTxt.text.toString(),
                        loginTxt.text.toString(),
                        pass.text.toString(),
                        0,
                    true
                )

                CoroutineScope(Dispatchers.IO).launch {
                    usersDAO.insert(user)
                }

                findNavController().navigate(R.id.to_home)
            } else {
                secondPass.setTextColor(Color.RED)
            }
        }

        return root
    }

}