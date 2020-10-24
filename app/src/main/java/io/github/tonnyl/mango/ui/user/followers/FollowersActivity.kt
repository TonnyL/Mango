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

package io.github.tonnyl.mango.ui.user.followers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.databinding.ActivityCommonBinding
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 2017/8/3.
 *
 * Show [io.github.tonnyl.mango.data.Follower]s of a [io.github.tonnyl.mango.data.User].
 */
class FollowersActivity : AppCompatActivity() {

    private lateinit var mFollowersFragment: FollowersFragment

    private lateinit var binding: ActivityCommonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(FollowersPresenter.EXTRA_FOLLOWERS_TITLE)

        savedInstanceState?.let {
            mFollowersFragment = supportFragmentManager.getFragment(it, FollowersFragment::class.java.simpleName) as FollowersFragment
        } ?: run {
            mFollowersFragment = FollowersFragment.newInstance()
        }

        if (!mFollowersFragment.isAdded) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, mFollowersFragment, FollowersFragment::class.java.simpleName)
                    .commit()
        }

        FollowersPresenter(mFollowersFragment, intent.getLongExtra(FollowersPresenter.EXTRA_USER_ID, 0L))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mFollowersFragment.isAdded) {
            supportFragmentManager.putFragment(outState, FollowersFragment::class.java.simpleName, mFollowersFragment)
        }
    }

}