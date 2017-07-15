package io.github.tonnyl.mango.main.shots

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.shot.ShotActivity
import io.github.tonnyl.mango.shot.ShotFragment

import org.jetbrains.anko.startActivity

import kotlinx.android.synthetic.main.fragment_shots_page.*

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class ShotsPageFragment : Fragment(), ShotsPageContract.View {

    private lateinit var mPresenter: ShotsPageContract.Presenter
    private var mAdapter: ShotsAdapter? = null
    private var mListSize = 0
    private var mIsLoading = false

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
        mPresenter.subscribe()

        initViews()

        refresh_layout.setOnRefreshListener {
            mAdapter?.clearData()
            mPresenter.listShots()
            mIsLoading = true
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as GridLayoutManager
                if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() == mListSize -1 && !mIsLoading) {
                    mPresenter.listShots()
                    mIsLoading = true
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: ShotsPageContract.Presenter) {
        mPresenter = presenter
    }

    override fun initViews() {
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(context, 2)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.post({
            refresh_layout.isRefreshing = loading
        })
    }

    override fun showResults(results: MutableList<Shot>) {
        mAdapter?.updateData(results) ?: run {
            mAdapter = ShotsAdapter(context, results)
            mAdapter?.setItemClickListener { _, position ->
                context.startActivity<ShotActivity>(ShotFragment.EXTRA_SHOT to results[position])
            }
            recycler_view.adapter = mAdapter
        }

        mListSize += results.size
        mIsLoading = false

        empty_view.visibility = if (results.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }

    override fun showMessage(message: String) {
        Snackbar.make(recycler_view, message, Snackbar.LENGTH_SHORT).show()
    }

}