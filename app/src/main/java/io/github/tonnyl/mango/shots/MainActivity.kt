package io.github.tonnyl.mango.shots

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.user.UserActivity
import io.github.tonnyl.mango.util.AccountManager
import io.github.tonnyl.mango.util.FrescoLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.startActivity

class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AccountManager.accessToken == null) {
            AccountManager.init(this)
        }

        nav_view.setCheckedItem(R.id.nav_explore)
        nav_view.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, ShotsFragment.newInstance(), ShotsFragment::class.java.simpleName)
                .commit()

        UserRepository.getAuthenticatedUser(AccountManager.accessToken?.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    AccountManager.authenticatedUser = user
                    updateUI()
                })

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_explore -> {

            }
            R.id.nav_buckets -> {

            }
            R.id.nav_projects -> {

            }
            R.id.nav_teams -> {

            }
            R.id.nav_settings -> {

            }
            R.id.nav_info -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun updateUI() {
        nav_view.getHeaderView(0).text_view_name.text = AccountManager.authenticatedUser?.name
        nav_view.getHeaderView(0).text_view_user_name.text = AccountManager.authenticatedUser?.username

        AccountManager.authenticatedUser?.let {
            FrescoLoader.loadAvatar(nav_view.getHeaderView(0).avatar_drawee, it.avatar_url)
            nav_view.getHeaderView(0).avatar_drawee.setOnClickListener {
                startActivity<UserActivity>(UserActivity.EXTRA_USER_ID to it.id)
            }
        }

    }

}
