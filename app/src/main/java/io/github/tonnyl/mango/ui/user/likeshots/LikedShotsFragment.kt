package io.github.tonnyl.mango.ui.user.likeshots

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.ui.shot.ShotActivity
import io.github.tonnyl.mango.ui.shot.ShotPresenter
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * Main ui for the user's liked shots screen.
 */

class LikedShotsFragment : Fragment(), LikedShotsContract.View {

    private lateinit var mPresenter: LikedShotsContract.Presenter
    private var mAdapter: LikedShotsAdapter? = null

    private var mIsLoading = false
    private var mLayoutManager: LinearLayoutManager? = null

    companion object {
        @JvmStatic
        fun newInstance(): LikedShotsFragment {
            return LikedShotsFragment()
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
            mPresenter.loadLikedShots()
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (mLayoutManager?.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1) && !mIsLoading) {
                    mIsLoading = true
                    mPresenter.loadMoreLikedShots()
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: LikedShotsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun showLikedShots(likeShots: List<LikedShot>) {
        recycler_view.layoutManager = mLayoutManager
        if (mAdapter == null) {
            mAdapter = LikedShotsAdapter(context, likeShots)
            mAdapter?.setItemClickListener({ _, position ->
                context.startActivity<ShotActivity>(ShotPresenter.EXTRA_SHOT to likeShots[position].shot)
            })
        }
        recycler_view.adapter = mAdapter
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        empty_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showNetworkError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
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
        recycler_view.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(context, 2)
    }

}