package io.github.tonnyl.mango.ui.user.following

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/29.
 *
 * Main ui for the user's following screen.
 */
class FollowingFragment : Fragment(), FollowingContract.View {

    private lateinit var mPresenter: FollowingContract.Presenter
    private var mAdapter: FollowingAdapter? = null

    private var mIsLoading = false
    private var mLayoutManager: LinearLayoutManager? = null

    companion object {
        @JvmStatic
        fun newInstance(): FollowingFragment {
            return FollowingFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        setHasOptionsMenu(true)

        mPresenter.subscribe()

        refresh_layout.setOnRefreshListener {
            mIsLoading = true
            mPresenter.loadFollowing()
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (mLayoutManager?.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1) && !mIsLoading) {
                    mIsLoading = true
                    mPresenter.loadMoreFollowing()
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: FollowingContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        empty_view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showNetworkError() {
        Snackbar.make(recycler_view, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showFollowings(followings: List<Followee>) {
        if (mAdapter == null) {
            mAdapter = FollowingAdapter(context, followings)
            mAdapter?.setOnItemClickListener { _, position ->
                context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to followings[position].followee)
            }
            recycler_view.adapter = mAdapter
        }

        mIsLoading = false
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
        mLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mLayoutManager
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    }

}