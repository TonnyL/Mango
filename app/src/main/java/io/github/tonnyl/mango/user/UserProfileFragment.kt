package io.github.tonnyl.mango.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class UserProfileFragment : Fragment(), UserProfileContract.View {

    private lateinit var mPresenter: UserProfileContract.Presenter

    companion object {

        @JvmStatic
        fun getInstance(): UserProfileFragment {
            return UserProfileFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity.onBackPressed()
        }
        return true
    }

    override fun setPresenter(presenter: UserProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun showUserInfo(user: User) {
        GlideLoader.loadAvatar(context, avatar, user.avatarUrl)
        user_info_twitter.text = user.links.twitter
        user_info_web.text = user.links.web
        user_name.text = user.username
        bio.text = user.bio
        title.text = user.username
        user_location.text = user.location

        tab_layout.getTabAt(0)?.text = getString(R.string.tab_title_shots).format(user.shotsCount)
        tab_layout.getTabAt(1)?.text = getString(R.string.tab_title_likes).format(user.likesCount)

    }

    private fun initViews() {
        val act = activity as UserProfileActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        act.supportActionBar?.setDisplayShowTitleEnabled(false)

        view_pager.adapter = UserProfilePagerAdapter(context, mPresenter.getUserId(), childFragmentManager)
        view_pager.offscreenPageLimit = 2

        tab_layout.setupWithViewPager(view_pager)
    }

}