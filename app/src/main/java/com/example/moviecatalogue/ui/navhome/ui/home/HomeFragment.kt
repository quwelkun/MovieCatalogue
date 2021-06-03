package com.example.moviecatalogue.ui.navhome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.moviecatalogue.R
import com.example.moviecatalogue.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    companion object {
        @StringRes
        val TAB_NAME = intArrayOf(R.string.movie, R.string.tvshow)
    }

    private lateinit var homeBinding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val sectionPagerAdapter = SectionHomePagerAdapter(this)
        homeBinding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(homeBinding.tabs,homeBinding.viewPager) { tab , position->
            tab.text = resources.getString(TAB_NAME[position])
        }.attach()

        homeBinding.tabs.isTabIndicatorFullWidth = true


        return homeBinding.root
    }


}