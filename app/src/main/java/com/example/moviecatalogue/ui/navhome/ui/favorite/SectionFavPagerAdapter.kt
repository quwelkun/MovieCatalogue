package com.example.moviecatalogue.ui.navhome.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviecatalogue.ui.movies.favoritemovie.MovieFavoriteFragment
import com.example.moviecatalogue.ui.tvshow.favoritetvshow.TvShowFavoriteFragment

class SectionFavPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MovieFavoriteFragment()
            1 -> TvShowFavoriteFragment()
            else -> Fragment()
        }
}
}