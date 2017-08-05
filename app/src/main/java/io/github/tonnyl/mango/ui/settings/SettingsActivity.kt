package io.github.tonnyl.mango.ui.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 2017/7/19.
 */

class SettingsActivity : AppCompatActivity() {

    private lateinit var mAboutFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setTitle(R.string.settings)
        }

        savedInstanceState?.let {
            mAboutFragment = supportFragmentManager.getFragment(it, SettingsFragment::class.java.simpleName) as SettingsFragment
        } ?: run {
            mAboutFragment = SettingsFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mAboutFragment, SettingsFragment::class.java.simpleName)
                .commit()

        SettingsPresenter(mAboutFragment)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mAboutFragment.isAdded) {
            supportFragmentManager.putFragment(outState, SettingsFragment::class.java.simpleName, mAboutFragment)
        }
    }

}