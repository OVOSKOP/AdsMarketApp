package com.collegeapp.advertmarketapp.utils.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.collegeapp.advertmarketapp.screens.auth.fragments.LoginFragment
import com.collegeapp.advertmarketapp.screens.auth.fragments.SignUpFragment

class AuthAdapter(
        fm: Fragment,
        private val totalTabs: Int): FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = totalTabs
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginFragment()
            }
            1 -> {
                SignUpFragment()
            }
            else -> createFragment(position)
        }
    }
}