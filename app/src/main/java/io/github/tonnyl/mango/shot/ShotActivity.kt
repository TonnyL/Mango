package io.github.tonnyl.mango.shot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@DeepLink("https://dribbble.com/shots/{id}")
class ShotActivity : AppCompatActivity() {

    private lateinit var mShotFragment: ShotFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mShotFragment = supportFragmentManager.getFragment(savedInstanceState, ShotFragment::class.java.simpleName) as ShotFragment
        } ?: run {
            mShotFragment = ShotFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mShotFragment, ShotFragment::class.java.simpleName)
                .commit()

        ShotPresenter(mShotFragment, intent.getParcelableExtra<Shot>(ShotPresenter.EXTRA_SHOT))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mShotFragment.isAdded) {
            supportFragmentManager.putFragment(outState, ShotFragment::class.java.simpleName, mShotFragment)
        }
    }

}