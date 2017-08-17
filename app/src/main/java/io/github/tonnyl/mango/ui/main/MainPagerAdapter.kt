package io.github.tonnyl.mango.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.ui.main.shots.ShotsPageFragment
import io.github.tonnyl.mango.ui.main.shots.ShotsPagePresenter

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class MainPagerAdapter(context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var mTitles = arrayOf<String>()
    private var mShotsPagePresenters = arrayListOf<ShotsPagePresenter>()
    private var mShotsPageFragments = arrayListOf<ShotsPageFragment>()

    init {
        mTitles = arrayOf(
                context.getString(R.string.popular),
                context.getString(R.string.following),
                context.getString(R.string.recent),
                context.getString(R.string.debuts)
        )
    }

    override fun getItem(position: Int): Fragment? {
        val fragment: ShotsPageFragment = if (mShotsPageFragments.size > position) {
            mShotsPageFragments[position]
        } else {
            ShotsPageFragment.newInstance()
        }
        when (position) {
            0 -> {
                mShotsPagePresenters.add(ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_POPULAR))
            }
            1 -> {
                mShotsPagePresenters.add(ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_FOLLOWING))
            }
            2 -> {
                mShotsPagePresenters.add(ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_RECENT))
            }
            else -> {
                mShotsPagePresenters.add(ShotsPagePresenter(fragment, ShotsPagePresenter.TYPE_DEBUTS))
            }
        }
        return fragment
    }

    override fun getCount() = mTitles.size

    override fun getPageTitle(position: Int) = mTitles[position]
}