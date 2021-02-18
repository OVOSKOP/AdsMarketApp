package com.collegeapp.advertmarketapp.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.collegeapp.advertmarketapp.R
import com.collegeapp.advertmarketapp.utils.adapters.AuthAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentAuth : Fragment() {

    private lateinit var frame: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_auth, container, false)
        frame = root.findViewById(R.id.main_frame_auth)

        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        val toolbar: Toolbar = root.findViewById(R.id.toolbar_auth)

        toolbar.title = "Профиль"
        toolbar.navigationIcon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_arrow_back_ios_new_24)
        toolbar.setNavigationOnClickListener {
            navController.popBackStack()
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