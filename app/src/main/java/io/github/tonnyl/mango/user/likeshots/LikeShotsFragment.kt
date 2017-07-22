package io.github.tonnyl.mango.user.likeshots

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.LikedShot
import kotlinx.android.synthetic.main.fragment_shots_page.*

/**
 * Created by lizhaotailang on 2017/7/19.
 */

class LikeShotsFragment : Fragment(), LikeShotsContract.View {

    private lateinit var mPresenter: LikeShotsContract.Presenter

    private var mAdapter: LikeShotsAdapter? = null

    companion object {
        fun newInstance(): LikeShotsFragment {
            return LikeShotsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shots_page, container, false)
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

    override fun setPresenter(presenter: LikeShotsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.isRefreshing = loading
    }

    override fun showShots(likeShots: MutableList<LikedShot>) {
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        if (mAdapter == null) {
            mAdapter = LikeShotsAdapter(context, likeShots)
        }
        recycler_view.adapter = mAdapter
    }

    private fun initViews() {
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recycler_view.setHasFixedSize(true)
    }

}