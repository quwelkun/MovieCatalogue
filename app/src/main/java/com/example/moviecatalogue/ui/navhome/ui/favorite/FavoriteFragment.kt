package com.example.moviecatalogue.ui.navhome.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.moviecatalogue.R
import com.example.moviecatalogue.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    companion object {
        @StringRes
        val TAB_NAME = intArrayOf(R.string.movie, R.string.tvshow)
    }

    private lateinit var favoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val sectionPagerAdapter = SectionFavPagerAdapter(this)
        favoriteBinding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(favoriteBinding.tabs,favoriteBinding.viewPager) { tab , position->
            tab.text = resources.getString(TAB_NAME[position])
        }.attach()

        favoriteBinding.tabs.isTabIndicatorFullWidth = true


        return favoriteBinding.root
    }
}