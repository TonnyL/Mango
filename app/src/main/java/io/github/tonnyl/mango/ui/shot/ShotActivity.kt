/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.ui.shot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Show the details of a [io.github.tonnyl.mango.data.Shot].
 */

@DeepLink("https://dribbble.com/shots/{id}")
class ShotActivity : AppCompatActivity() {

    private lateinit var mShotFragment: ShotFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mShotFragment = supportFragmentManager.getFragment(it, ShotFragment::class.java.simpleName) as ShotFragment
        } ?: run {
            mShotFragment = ShotFragment.newInstance()
        }

        if (!mShotFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, mShotFragment, ShotFragment::class.java.simpleName)
                    .commit()
        }

        val isDeepLink = intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)
        val shotId: Long
        // hook the presenter
        if (isDeepLink) {
            var id = intent.extras.getString("id")
            // https://dribbble.com/shots/3495164-Google-people and https://dribbble.com/shots/3495164
            // are both valid
            val dashIndex = if (id.isEmpty()) -1 else id.indexOf("-")
            if (dashIndex != -1) {
                id = id.substring(0, dashIndex)
            }
            shotId = id.toLong()
            ShotPresenter(mShotFragment, shotId)

        } else {
            ShotPresenter(mShotFragment, intent.getParcelableExtra<Shot>(ShotPresenter.EXTRA_SHOT))
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mShotFragment.isAdded) {
            supportFragmentManager.putFragment(outState, ShotFragment::class.java.simpleName, mShotFragment)
        }
    }

}