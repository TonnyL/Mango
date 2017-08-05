package io.github.tonnyl.mango.ui.user.followers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/29.
 */
class FollowersFragment : Fragment(), FollowersContract.View {

    private lateinit var mPresenter: FollowersContract.Presenter
    private var mAdapter: FollowersAdapter? = null

    private var mLayoutManager: LinearLayoutManager? = null

    companion object {
        @JvmStatic
        fun newInstance(): FollowersFragment {
            return FollowersFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: FollowersContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun showFollowers(followers: MutableList<Follower>) {
        // todo
        mAdapter?.notifyDataSetChanged() ?: run {
            mAdapter = FollowersAdapter(context, followers)
            mAdapter?.setOnItemClickListener { _, position ->
                context.startActivity<UserProfileActivity>(UserProfileActivity.EXTRA_USER to followers[position].follower)
            }
            recycler_view.adapter = mAdapter
        }

        empty_view.visibility = if (followers.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun initViews() {
        mLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mLayoutManager
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    }

}