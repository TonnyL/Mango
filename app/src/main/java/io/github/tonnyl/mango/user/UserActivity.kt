package io.github.tonnyl.mango.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class UserActivity: AppCompatActivity() {

    private lateinit var mUserFragment: UserFragment

    companion object {
        val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        if (savedInstanceState != null) {
            mUserFragment = supportFragmentManager.getFragment(savedInstanceState, UserFragment::class.java.simpleName) as UserFragment
        } else {
            mUserFragment = UserFragment.getInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mUserFragment, UserFragment::class.java.simpleName)
                .commit()

        UserPresenter(mUserFragment)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mUserFragment.isAdded) {
            supportFragmentManager.putFragment(outState, UserFragment::class.java.simpleName, mUserFragment)
        }
    }

}