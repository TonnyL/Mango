package io.github.tonnyl.mango.shot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.shot.comments.CommentsFragment
import io.github.tonnyl.mango.shot.comments.CommentsPresenter
import io.github.tonnyl.mango.shot.likes.LikesFragment
import io.github.tonnyl.mango.shot.likes.LikesPresenter

/**
 * Created by lizhaotailang on 2017/7/3.
 */
class ListActivity : AppCompatActivity() {

    private var mCommentsFragment: CommentsFragment? = null
    private var mLikesFragment: LikesFragment? = null

    private var mId: Long = 0L
    private var mType: Int = 0

    companion object {
        val EXTRA_TYPE = "EXTRA_TYPE"
        val EXTRA_ID = "EXTRA_ID"

        val TYPE_COMMENTS = 0x00
        val TYPE_LIKES = 0x01
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        mType = intent.getIntExtra(EXTRA_TYPE, 0x00)
        mId = intent.getLongExtra(EXTRA_ID, 0L)

        if (mType == TYPE_COMMENTS) {
            savedInstanceState?.let {
                mCommentsFragment = supportFragmentManager.getFragment(it, CommentsFragment::class.java.simpleName) as? CommentsFragment
            } ?: run {
                mCommentsFragment = CommentsFragment.newInstance()
            }

            mCommentsFragment?.let {
                supportFragmentManager.beginTransaction()
                        .add(R.id.container, it, CommentsFragment::class.java.simpleName)
                        .commit()
                CommentsPresenter(it, mId)
            }
        } else {
            savedInstanceState?.let {
                mLikesFragment = supportFragmentManager.getFragment(it, LikesFragment::class.java.simpleName) as? LikesFragment
            } ?: run {
                mLikesFragment = LikesFragment.newInstance()
            }

            mLikesFragment?.let {
                supportFragmentManager.beginTransaction()
                        .add(R.id.container, it, LikesFragment::class.java.simpleName)
                        .commit()
                LikesPresenter(it, mId)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mCommentsFragment?.let {
            if (it.isAdded) {
                supportFragmentManager.putFragment(outState, CommentsFragment::class.java.simpleName, mCommentsFragment)
            }
        }
        mLikesFragment?.let {
            if (it.isAdded) {
                supportFragmentManager.putFragment(outState, LikesFragment::class.java.simpleName, mLikesFragment)
            }
        }
    }

}