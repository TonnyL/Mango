package io.github.tonnyl.mango.ui.main.shots

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.ui.shot.ShotActivity
import io.github.tonnyl.mango.ui.shot.ShotPresenter
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/29.
 *
 * Main ui for the shots page screen.
 */

class ShotsPageFragment : Fragment(), ShotsPageContract.View {

    private lateinit var mPresenter: ShotsPageContract.Presenter
    private var mAdapter: ShotsAdapter? = null
    private var mIsLoading = false

    companion object {
        @JvmStatic
        fun newInstance(): ShotsPageFragment {
            return ShotsPageFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        initViews()

        refresh_layout.setOnRefreshListener {
            mIsLoading = true
            mPresenter.listShots()
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                if (dy > 0 && layoutManager.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1 && !mIsLoading) {
                    mPresenter.listMoreShots()
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
        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.post({
            refresh_layout.isRefreshing = loading
        })
    }

    override fun showResults(results: List<Shot>) {
        if (mAdapter == null) {
            mAdapter = ShotsAdapter(context, results)
            mAdapter?.setItemClickListener(object : OnRecyclerViewItemClickListener {

                override fun onItemClick(view: View, position: Int) {
                    context.startActivity<ShotActivity>(ShotPresenter.EXTRA_SHOT to results[position])
                }

                override fun onAvatarClick(view: View, position: Int) {
                    results[position].user?.let {
                        context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to it)
                    }
                }

            })
            recycler_view.adapter = mAdapter
        }

        mIsLoading = false

    }

    override fun showNetworkError() {
        Snackbar.make(recycler_view, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun setEmptyContentVisibility(visible: Boolean) {
        empty_view.visibility = if (visible && (mAdapter == null)) View.VISIBLE else View.GONE
    }

    override fun notifyDataAllRemoved(size: Int) {
        mAdapter?.notifyItemRangeRemoved(0, size)
        mIsLoading = false
    }

    override fun notifyDataAdded(startPosition: Int, size: Int) {
        mAdapter?.notifyItemRangeInserted(startPosition, size)
        mIsLoading = false
    }

}