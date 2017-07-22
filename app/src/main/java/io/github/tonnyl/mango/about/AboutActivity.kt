package io.github.tonnyl.mango.about

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class AboutActivity : AppCompatActivity() {

    private lateinit var mAboutFragment: AboutFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mAboutFragment = supportFragmentManager.getFragment(it, AboutFragment::class.java.simpleName) as AboutFragment
        } ?: run {
            mAboutFragment = AboutFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mAboutFragment, AboutFragment::class.java.simpleName)
                .commit()

        AboutPresenter(mAboutFragment)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mAboutFragment.isAdded) {
            supportFragmentManager.putFragment(outState, AboutFragment::class.java.simpleName, mAboutFragment)
        }
    }

}