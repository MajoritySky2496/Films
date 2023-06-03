package com.example.films.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.films.ui.detail.AboutFragment
import com.example.films.ui.detail.PosterFragment

class DetailsViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,
                              private val posterUrl: String, private val movieId: String):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
         when(position){
            0 -> return PosterFragment().newInstance(posterUrl)
            else -> return AboutFragment().newInstance(movieId)
        }
    }

}