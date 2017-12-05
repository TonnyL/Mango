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

package io.github.tonnyl.mango.ui.user.shots

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.ui.shot.ShotActivity
import io.github.tonnyl.mango.ui.shot.ShotPresenter
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * Main ui for the user's shots screen.
 */

class ShotsFragment : Fragment(), ShotsContract.View {

    private lateinit var mPresenter: ShotsContract.Presenter

    private var mIsLoading = false
    private var mAdapter: ShotsAdapter? = null

    companion object {
        @JvmStatic
        fun newInstance(): ShotsFragment {
            return ShotsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

        refresh_layout.setOnRefreshListener {
            mIsLoading = true
            mPresenter.loadShots()
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as GridLayoutManager
                if (dy > 0 && layoutManager.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1 && !mIsLoading) {
                    Log.d("view", "load")
                    mPresenter.loadShotsOfNextPage()
                    mIsLoading = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: ShotsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun showShots(shots: List<Shot>) {
        if (mAdapter == null) {
            mAdapter = ShotsAdapter(context, shots)
            mAdapter?.setItemClickListener({ _, position ->
                context.startActivity<ShotActivity>(ShotPresenter.EXTRA_SHOT to shots[position])
            })
            recycler_view.adapter = mAdapter
        }

        mIsLoading = false
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        empty_view.visibility = if (visible && (mAdapter == null)) View.VISIBLE else View.GONE
    }

    override fun showNetworkError() {
        Snackbar.make(recycler_view, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun notifyDataAllRemoved(size: Int) {
        mAdapter?.notifyItemRangeRemoved(0, size)
        mIsLoading = false
    }

    override fun notifyDataAdded(startPosition: Int, size: Int) {
        mAdapter?.notifyItemRangeInserted(startPosition, size)
        mIsLoading = false
    }

    private fun initViews() {
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.setHasFixedSize(true)
    }

}