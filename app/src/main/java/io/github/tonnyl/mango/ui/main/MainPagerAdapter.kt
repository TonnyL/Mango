/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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