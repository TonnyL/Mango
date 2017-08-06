package io.github.tonnyl.mango.ui.user

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class UserProfileActivity : AppCompatActivity() {

    private lateinit var mUserProfileFragment: UserProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mUserProfileFragment = supportFragmentManager.getFragment(savedInstanceState, UserProfileFragment::class.java.simpleName) as UserProfileFragment
        } ?: run {
            mUserProfileFragment = UserProfileFragment.getInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mUserProfileFragment, UserProfileFragment::class.java.simpleName)
                .commit()

        UserProfilePresenter(mUserProfileFragment, intent.getParcelableExtra(UserProfilePresenter.EXTRA_USER))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mUserProfileFragment.isAdded) {
            supportFragmentManager.putFragment(outState, UserProfileFragment::class.java.simpleName, mUserProfileFragment)
        }
    }

}