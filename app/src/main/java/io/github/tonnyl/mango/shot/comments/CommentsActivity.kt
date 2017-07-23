package io.github.tonnyl.mango.shot.comments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import kotlinx.android.synthetic.main.activity_common.*

/**
 * Created by lizhaotailang on 2017/7/22.
 */

class CommentsActivity : AppCompatActivity() {

    private lateinit var mCommentsFragment: CommentsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setTitle(R.string.comments)
        }

        savedInstanceState?.let {
            mCommentsFragment = supportFragmentManager.getFragment(it, CommentsFragment::class.java.simpleName) as CommentsFragment
        } ?: run {
            mCommentsFragment = CommentsFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mCommentsFragment, CommentsFragment::class.java.simpleName)
                .commit()

        CommentsPresenter(mCommentsFragment, intent.getLongExtra(EXTRA_ID, 0L))

    }

    companion object {
        @JvmField
        val EXTRA_ID = "EXTRA_ID"
    }

}