package io.github.tonnyl.mango.shots

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.AccountManager
import io.github.tonnyl.mango.util.FrescoLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (AccountManager.accessToken == null) {
            AccountManager.init(this)
        }

        navView.setNavigationItemSelectedListener(this)

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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun updateUI() {
        navView.getHeaderView(0).textViewName.text = AccountManager.authenticatedUser?.name
        navView.getHeaderView(0).textViewUserName.text = AccountManager.authenticatedUser?.username
        FrescoLoader.loadAvatar(navView.getHeaderView(0).avatarDrawee, AccountManager.authenticatedUser!!.avatar_url)
    }

}
