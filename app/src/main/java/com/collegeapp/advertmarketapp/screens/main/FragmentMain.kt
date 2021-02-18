package com.collegeapp.advertmarketapp.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.collegeapp.advertmarketapp.App
import com.collegeapp.advertmarketapp.MainActivity
import com.collegeapp.advertmarketapp.R
import com.google.android.material.appbar.CollapsingToolbarLayout

class FragmentMain : Fragment() {

    private lateinit var openProfile: View
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_main, container, false)

        val user = (requireContext().applicationContext as App).currentUser

        val toolbar: Toolbar = root.findViewById(R.id.toolbar)
        root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = requireActivity().title

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        if (user == null) {
            openProfile = root.findViewById(R.id.open_profile)
            openProfile.setOnClickListener {
                navController.navigate(R.id.to_auth)
            }
        } else {
            val profileLabel = root.findViewById<TextView>(R.id.profile_txt)
            profileLabel.text = "${user.name} (${user.login})"
        }


        return root
    }

    override fun onResume() {
        super.onResume()
        root.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = getString(R.string.app_name)
    }

}