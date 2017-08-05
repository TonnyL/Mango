package io.github.tonnyl.mango.ui.user.shots

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
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
 */

class ShotsFragment : Fragment(), ShotsContract.View {

    private lateinit var mPresenter: ShotsContract.Presenter

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

    override fun showShots(shots: MutableList<Shot>) {
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        if (mAdapter == null) {
            mAdapter = ShotsAdapter(context, shots)
            mAdapter?.setItemClickListener({ _, position ->
                context.startActivity<ShotActivity>(ShotPresenter.EXTRA_SHOT to shots[position])
            })
        }
        recycler_view.adapter = mAdapter
    }

    private fun initViews() {
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        recycler_view.setHasFixedSize(true)
    }

}