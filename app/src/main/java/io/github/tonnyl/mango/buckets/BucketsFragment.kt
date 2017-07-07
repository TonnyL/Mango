package io.github.tonnyl.mango.buckets

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class BucketsFragment: Fragment(), BucketsContract.View {

    private lateinit var mPresenter: BucketsContract.Presenter

    companion object {
        fun newInstance(): BucketsFragment {
            return BucketsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_buckets, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setPresenter(presenter: BucketsContract.Presenter) {
        mPresenter = presenter
    }
}