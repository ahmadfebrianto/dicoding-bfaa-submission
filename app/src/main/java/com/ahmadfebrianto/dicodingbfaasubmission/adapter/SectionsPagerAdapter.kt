package com.ahmadfebrianto.dicodingbfaasubmission.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahmadfebrianto.dicodingbfaasubmission.R
import com.ahmadfebrianto.dicodingbfaasubmission.ui.userdetail.UserListFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    var username: String? = null

    private val tabTables = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return UserListFragment.newInstance(position + 1, username)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTables[position])
    }

}