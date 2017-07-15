package io.github.tonnyl.mango.auth

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.main.MainActivity
import io.github.tonnyl.mango.util.Constants

/**
 * Created by lizhaotailang on 2017/6/24.
 */

class AuthActivity: AppCompatActivity() {

    private lateinit var mFragment: AuthFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.IS_USER_LOGGED_IN, false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
            return
        }

        setContentView(R.layout.container)

        savedInstanceState?.let {
            mFragment = supportFragmentManager.getFragment(savedInstanceState, AuthFragment::class.java.simpleName) as AuthFragment
        } ?: run {
            mFragment = AuthFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mFragment, AuthFragment::class.java.simpleName)
                .commit()

        AuthPresenter(mFragment)

        mFragment.handleAuthCallback(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mFragment.handleAuthCallback(intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mFragment.isAdded) {
            supportFragmentManager.putFragment(outState, AuthFragment::class.java.simpleName, mFragment)
        }
    }

}