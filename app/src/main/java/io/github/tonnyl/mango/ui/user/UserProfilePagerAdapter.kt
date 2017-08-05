package io.github.tonnyl.mango.ui.user

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.ui.user.likeshots.LikedShotsFragment
import io.github.tonnyl.mango.ui.user.likeshots.LikedShotsPresenter
import io.github.tonnyl.mango.ui.user.shots.ShotsFragment
import io.github.tonnyl.mango.ui.user.shots.ShotsPresenter

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class UserProfilePagerAdapter(context: Context, userId: Long, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var mTitles = arrayOf<String>()
    private val mUserId = userId

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
                ShotsPresenter(fragment, mUserId)
            }
            else -> {
                fragment = LikedShotsFragment.newInstance()
                LikedShotsPresenter(fragment, mUserId)
            }
        }
        return fragment
    }

    override fun getPageTitle(position: Int) = mTitles[position]

}