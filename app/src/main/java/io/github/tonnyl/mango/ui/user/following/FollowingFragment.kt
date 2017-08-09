package io.github.tonnyl.mango.ui.user.following

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
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

        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: FollowingContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun showFollowings(followings: MutableList<Followee>) {
        // todo
        mAdapter?.notifyDataSetChanged() ?: run {
            mAdapter = FollowingAdapter(context, followings)
            mAdapter?.setOnItemClickListener { _, position ->
                context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to followings[position].followee)
            }
            recycler_view.adapter = mAdapter
        }

        empty_view.visibility = if (followings.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun initViews() {
        mLayoutManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mLayoutManager
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    }

}