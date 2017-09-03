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

package io.github.tonnyl.mango.ui.user

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.ui.user.likeshots.LikedShotsFragment
import io.github.tonnyl.mango.ui.user.likeshots.LikedShotsPresenter
import io.github.tonnyl.mango.ui.user.shots.ShotsFragment
import io.github.tonnyl.mango.ui.user.shots.ShotsPresenter

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class UserProfilePagerAdapter(context: Context, user: User, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var mTitles = arrayOf<String>()
    private val mUser = user

    init {
        mTitles = arrayOf(
                context.getString(R.string.tab_title_shots),
                context.getString(R.string.tab_title_likes)
        )
    }

    override fun getCount() = mTitles.size

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment
        when (position) {
            0 -> {
                fragment = ShotsFragment.newInstance()
                ShotsPresenter(fragment, mUser)
            }
            else -> {
                fragment = LikedShotsFragment.newInstance()
                LikedShotsPresenter(fragment, mUser.id)
            }
        }
        return fragment
    }

    override fun getPageTitle(position: Int) = mTitles[position]

}