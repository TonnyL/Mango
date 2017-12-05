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

import android.content.pm.ShortcutManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.*
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.ui.auth.AuthActivity
import io.github.tonnyl.mango.ui.settings.SettingsActivity
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import io.github.tonnyl.mango.util.AccessTokenManager
import io.github.tonnyl.mango.util.Constants
import kotlinx.android.synthetic.main.fragment_shots.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Main ui for the main screen.
 */

class MainFragment : Fragment(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter
    private var mPagerAdapter: MainPagerAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_shots, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

        user_info_layout.setOnClickListener {
            mPresenter.getUser()?.let {
                context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to it)
            }
        }

        // Handle the intent actions from app shortcuts
        activity.intent.action?.let {
            view_pager.currentItem = when (it) {
                Constants.INTENT_ACTION_FOLLOWING -> 1
                Constants.INTENT_ACTION_RECENT -> 2
                Constants.INTENT_ACTION_DEBUTS -> 3
                else -> 0
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_logout) {
            showLogoutDialog()
        } else if (id == R.id.action_settings) {
            context.startActivity<SettingsActivity>()
        }
        return true
    }

    override fun initViews() {
        (activity as MainActivity).setSupportActionBar(toolbar)

        mPagerAdapter = MainPagerAdapter(context, childFragmentManager)
        view_pager.adapter = mPagerAdapter
        view_pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(view_pager)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun showAuthUserInfo(user: User) {
        user_name.text = user.name
        GlideLoader.loadAvatar(avatar, user.avatarUrl)
    }

    override fun navigateToLogin() {
        AccessTokenManager.clear()
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(Constants.IS_USER_LOGGED_IN, false)
                .putString(Constants.ACCESS_TOKEN, null)
                .apply()
        context.startActivity(context.intentFor<AuthActivity>().newTask().clearTask())
        activity.finish()
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(context)
                .setTitle(R.string.log_out)
                .setMessage(getString(R.string.logout_message))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    mPresenter.logoutUser()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->

                }
                .show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mPagerAdapter != null) {
            mPagerAdapter
        }
    }

    override fun disableShortcuts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            activity.getSystemService(ShortcutManager::class.java)
                    .disableShortcuts(arrayListOf(Constants.SHORTCUT_ID_POPULAR,
                            Constants.SHORTCUT_ID_FOLLOWING,
                            Constants.SHORTCUT_ID_RECENT,
                            Constants.SHORTCUT_ID_DEBUTS))
        }
    }

}