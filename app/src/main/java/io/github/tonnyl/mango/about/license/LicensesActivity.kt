package io.github.tonnyl.mango.about.license

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/21.
 */

class LicensesActivity : AppCompatActivity() {

    private var mLicensesFragment: LicensesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mLicensesFragment = supportFragmentManager.getFragment(it, LicensesFragment::class.java.simpleName) as LicensesFragment
        } ?: run {
            mLicensesFragment = LicensesFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mLicensesFragment, LicensesFragment::class.java.simpleName)
                .commit()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mLicensesFragment?.let {
            if (it.isAdded) {
                supportFragmentManager.putFragment(outState, LicensesFragment::class.java.simpleName, it)
            }
        }
    }

}