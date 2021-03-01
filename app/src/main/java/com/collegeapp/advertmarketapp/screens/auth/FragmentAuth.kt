package com.collegeapp.advertmarketapp.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.utils.adapters.AuthAdapter
import com.collegeapp.advertmarketapp.utils.app
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentAuth : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_auth, container, false)

        if (app.currentUser != null) {
            findNavController().navigate(R.id.to_home)
        }

        val tabLayout = root.findViewById<TabLayout>(R.id.tabs)
        val tabsItems = root.findViewById<ViewPager2>(R.id.tabs_item)
        tabsItems.adapter = AuthAdapter(this, tabLayout.tabCount)

        TabLayoutMediator(tabLayout, tabsItems) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Вход"
                }
                1 -> {
                    tab.text = "Регистрация"
                }
            }
        }.attach()

        return root
    }

}