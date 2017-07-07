package io.github.tonnyl.mango.shots

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_shots.*

/**
 * Created by lizhaotailang on 2017/6/28.
 */
class ShotsFragment : Fragment(), ShotsContract.View {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mPresenter: ShotsContract.Presenter

    companion object {
        fun newInstance(): ShotsFragment {
            return ShotsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shots, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun initViews() {

        toggle = ActionBarDrawerToggle(
                activity, activity.drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity.drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        view_pager.adapter = ShotsPagerAdapter(context, childFragmentManager)
        view_pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(view_pager)
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun setPresenter(presenter: ShotsContract.Presenter) {
        mPresenter = presenter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            activity.drawer_layout.removeDrawerListener(toggle)
        } else {
            activity.drawer_layout.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

}