package io.github.tonnyl.mango.ui.settings.license

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 2017/7/21.
 *
 * Show the open source licenses.
 */

class LicensesActivity : AppCompatActivity() {

    private lateinit var mLicensesFragment: LicensesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setTitle(R.string.open_source_licenses)
        }

        savedInstanceState?.let {
            mLicensesFragment = supportFragmentManager.getFragment(it, LicensesFragment::class.java.simpleName) as LicensesFragment
        } ?: run {
            mLicensesFragment = LicensesFragment.newInstance()
        }

        if (!mLicensesFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, mLicensesFragment, LicensesFragment::class.java.simpleName)
                    .commit()
        }

        LicensesPresenter(mLicensesFragment)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mLicensesFragment.isAdded) {
            supportFragmentManager.putFragment(outState, LicensesFragment::class.java.simpleName, mLicensesFragment)
        }
    }

}