package io.github.tonnyl.mango.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/3.
 */
class ListActivity : AppCompatActivity() {

    private var mListFragment: ListFragment? = null

    companion object {
        val EXTRA_TYPE = "EXTRA_TYPE"
        val EXTRA_ID = "EXTRA_ID"

        val TYPE_COMMENTS = 0x00
        val TYPE_LIKES = 0x01
        val TYPE_ATTACHMENTS = 0x02
        val TYPE_BUCKETS = 0x03
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        if (savedInstanceState != null) {
            mListFragment = supportFragmentManager.getFragment(savedInstanceState, ListFragment::class.java.simpleName) as ListFragment
        } else {
            mListFragment = ListFragment.newInstance()
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mListFragment, ListFragment::class.java.simpleName)
                .commit()

        ListPresenter(mListFragment!!)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mListFragment!!.isAdded) {
            supportFragmentManager.putFragment(outState, ListFragment::class.java.simpleName, mListFragment)
        }
    }

}