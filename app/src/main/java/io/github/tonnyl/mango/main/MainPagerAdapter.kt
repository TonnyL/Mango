package io.github.tonnyl.mango.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.main.shots.ShotsPageFragment
import io.github.tonnyl.mango.main.shots.ShotsPagePresenter

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class MainPagerAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var mTitles = arrayOf<String>()

    init {
        mTitles = arrayOf(
                context.getString(R.string.popular),
                context.getString(R.string.following),
                context.getString(R.string.recent),
                context.getString(R.string.debuts)
        )
    }

    override fun getItem(position: Int): Fragment? {
        val fragment = ShotsPageFragment.newInstance()
        when (position) {
            0 -> ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_POPULAR)
            1 -> ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_FOLLOWING)
            2 -> ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_RECENT)
            else -> ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_DEBUTS)
        }
        return fragment
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }
}