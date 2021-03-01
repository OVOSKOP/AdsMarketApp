package com.collegeapp.advertmarketapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.collegeapp.advertmarketapp.screens.order.FragmentOrder
import com.collegeapp.advertmarketapp.utils.CHOOSE_FILE
import com.collegeapp.advertmarketapp.utils.CHOOSE_FILE_RES


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return

        val uri = data?.data

        println("Path  = $uri")
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val fm = navHostFragment!!.childFragmentManager.fragments[0]

        (fm as FragmentOrder).setUrls(uri, requestCode)

    }
}