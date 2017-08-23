package io.github.tonnyl.mango.ui.user

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Html
import android.view.*
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.ui.user.followers.FollowersActivity
import io.github.tonnyl.mango.ui.user.followers.FollowersPresenter
import io.github.tonnyl.mango.ui.user.following.FollowingActivity
import io.github.tonnyl.mango.ui.user.following.FollowingPresenter
import kotlinx.android.synthetic.main.fragment_user_profile.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.startActivity


/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Main ui for the user profile screen.
 */

class UserProfileFragment : Fragment(), UserProfileContract.View {

    private lateinit var mPresenter: UserProfileContract.Presenter
    private var mFollowable = false
    private var mIsFollowing = false

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

        following.setOnClickListener {
            context.startActivity<FollowingActivity>(
                    FollowingPresenter.EXTRA_USER_ID to mPresenter.getUser().id,
                    FollowingPresenter.EXTRA_FOLLOWING_TITLE to following.text)
        }

        followers.setOnClickListener {
            context.startActivity<FollowersActivity>(
                    FollowersPresenter.EXTRA_USER_ID to mPresenter.getUser().id,
                    FollowersPresenter.EXTRA_FOLLOWERS_TITLE to followers.text)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_user, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu?.getItem(0)?.subMenu?.getItem(0)
        menuItem?.isVisible = mFollowable
        if (mIsFollowing) {
            menuItem?.setIcon(R.drawable.ic_user_minus_black_24dp)
            menuItem?.setTitle(R.string.unfollow)
        } else {
            menuItem?.setIcon(R.drawable.ic_user_plus_black_24dp)
            menuItem?.setTitle(R.string.follow)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> activity.onBackPressed()
            R.id.action_follow_unfollow -> mPresenter.toggleFollow()
            R.id.action_open_in_browser -> context.browse(mPresenter.getUser().htmlUrl)
        }
        activity.invalidateOptionsMenu()
        return true
    }

    override fun setPresenter(presenter: UserProfileContract.Presenter) {
        mPresenter = presenter
    }

    override fun showUserInfo(user: User) {
        GlideLoader.loadAvatar(context, avatar, user.avatarUrl)

        user.links.twitter?.let {
            user_info_twitter.text = it
        } ?: run {
            user_info_twitter.visibility = View.GONE
        }

        user.links.web?.let {
            user_info_web.text = it
        } ?: run {
            user_info_web.visibility = View.GONE
        }

        user.location?.let {
            user_info_location.text = it
        } ?: run {
            user_info_location.visibility = View.GONE
        }

        followers.text = getString(R.string.followers_formatted).format(user.followersCount)
        following.text = getString(R.string.following_formatted).format(user.followingsCount)

        if (Build.VERSION.SDK_INT >= 24) {
            bio.text = Html.fromHtml(user.bio, Html.FROM_HTML_MODE_LEGACY)
        } else {
            bio.text = Html.fromHtml(user.bio)
        }

        tab_layout.getTabAt(0)?.text = getString(R.string.tab_title_shots).format(user.shotsCount)
        tab_layout.getTabAt(1)?.text = getString(R.string.tab_title_likes).format(user.likesCount)

        val act = activity as UserProfileActivity
        act.supportActionBar?.title = user.name
        act.supportActionBar?.subtitle = user.username

    }

    override fun setFollowing(isFollowing: Boolean) {
        mIsFollowing = isFollowing
        activity.invalidateOptionsMenu()
    }

    override fun setFollowable(followable: Boolean) {
        mFollowable = followable
        activity.invalidateOptionsMenu()
    }

    override fun showNetworkError() {
        Snackbar.make(view_pager, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    private fun initViews() {

        val act = activity as UserProfileActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        view_pager.adapter = UserProfilePagerAdapter(context, mPresenter.getUser(), childFragmentManager)
        view_pager.offscreenPageLimit = 2

        tab_layout.setupWithViewPager(view_pager)
    }

}