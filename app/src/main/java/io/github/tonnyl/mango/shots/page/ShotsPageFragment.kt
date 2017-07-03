package io.github.tonnyl.mango.shots.page

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.interfaze.OnRecyclerViewItemClickListener

import kotlinx.android.synthetic.main.fragment_shots_page.*

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class ShotsPageFragment : Fragment(), ShotsPageContract.View {

    private var mPresenter: ShotsPageContract.Presenter? = null
    private var mAdapter: ShotItemAdapter? = null

    companion object {

        fun newInstance(): ShotsPageFragment {
            return ShotsPageFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shots_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter?.subscribe()

        initViews()

        refreshLayout.setOnRefreshListener {
            mPresenter?.listShots()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.unsubscribe()
    }

    override fun setPresenter(presenter: ShotsPageContract.Presenter) {
        mPresenter = presenter
    }

    override fun initViews() {
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refreshLayout.post({
            refreshLayout.isRefreshing = loading
        })
    }

    override fun showResults(results: MutableList<Shot>) {
        if (mAdapter == null) {
            mAdapter = ShotItemAdapter(context, results)
            mAdapter?.setItemClickListener(object : OnRecyclerViewItemClickListener {
                override fun OnItemClick(v: View, position: Int) {

                }
            })
            recyclerView.adapter = mAdapter
        } else {
            mAdapter?.updateData(results)
        }

        emptyView.visibility = if (results.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }

    override fun showMessage(message: String) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show()
    }

}