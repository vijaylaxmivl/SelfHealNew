package com.cm.selfheal.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cm.selfheal.R
import com.cm.selfheal.view.fragment.OverviewFragment
import com.cm.selfheal.view.fragment.ThreatListFragment


private val TAB_TITLES = arrayOf(
    R.string.overview_tab_text,
    R.string.threat_tab_text
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position==0){
            return OverviewFragment.newInstance(0)
        }

        return ThreatListFragment.newInstance(1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}