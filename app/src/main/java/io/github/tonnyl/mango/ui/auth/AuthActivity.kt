package io.github.tonnyl.mango.ui.auth

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.ui.main.MainActivity
import io.github.tonnyl.mango.util.Constants

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * Show authentication view.
 */

class AuthActivity: AppCompatActivity() {

    private lateinit var mAuthFragment: AuthFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Important!
        AuthUserRepository.init(this@AuthActivity)

        // If the user has logged in already, do not display the ui and go to homepage immediately.
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(Constants.IS_USER_LOGGED_IN, false)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
            return
        }

        setContentView(R.layout.container)

        savedInstanceState?.let {
            mAuthFragment = supportFragmentManager.getFragment(savedInstanceState, AuthFragment::class.java.simpleName) as AuthFragment
        } ?: run {
            mAuthFragment = AuthFragment.newInstance()
        }

        if (!mAuthFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, mAuthFragment, AuthFragment::class.java.simpleName)
                    .commit()
        }

        AuthPresenter(mAuthFragment)

        mAuthFragment.handleAuthCallback(intent)
    }

    // Handle the result when user logged in successfully in browser.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        mAuthFragment.handleAuthCallback(intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mAuthFragment.isAdded) {
            supportFragmentManager.putFragment(outState, AuthFragment::class.java.simpleName, mAuthFragment)
        }
    }

}