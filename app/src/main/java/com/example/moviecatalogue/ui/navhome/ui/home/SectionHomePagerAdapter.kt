package com.example.moviecatalogue.ui.navhome.ui.home

import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviecatalogue.ui.movies.homemovie.MoviesFragment
import com.example.moviecatalogue.ui.tvshow.hometvshow.TvShowFragment

class SectionHomePagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MoviesFragment()
            1 -> TvShowFragment()
            else -> Fragment()
        }
    }
}