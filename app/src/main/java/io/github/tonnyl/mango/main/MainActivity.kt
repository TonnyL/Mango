package io.github.tonnyl.mango.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.util.AccountManager

class MainActivity: AppCompatActivity() {

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mainFragment = fragmentManager.getFragment(it, MainFragment::class.java.simpleName) as MainFragment
        } ?: run {
            mainFragment = MainFragment.newInstance()
        }

        if (AccountManager.accessToken == null) {
            AccountManager.init(this)
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mainFragment, MainFragment::class.java.simpleName)
                .commit()

        MainPresenter(mainFragment)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mainFragment.isAdded) {
            supportFragmentManager.putFragment(outState, MainFragment::class.java.simpleName, mainFragment)
        }
    }

}
