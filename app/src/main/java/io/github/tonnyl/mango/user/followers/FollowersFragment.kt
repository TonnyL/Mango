package io.github.tonnyl.mango.user.followers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Follower
import kotlinx.android.synthetic.main.fragment_simple_list.*

/**
 * Created by lizhaotailang on 2017/7/29.
 */
class FollowersFragment : Fragment(), FollowersContract.View {

    private lateinit var mPresenter: FollowersContract.Presenter

    companion object {
        fun newInstance(): FollowersFragment {
            return FollowersFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_simple_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}