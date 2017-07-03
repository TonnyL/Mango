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

    private var toggle: ActionBarDrawerToggle? = null
    private var mPresenter: ShotsContract.Presenter? = null

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
                activity, activity.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity.drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()

        viewPager.adapter = ShotsPagerAdapter(context, childFragmentManager)
        viewPager.offscreenPageLimit = 4

        tabLayout.setupWithViewPager(viewPager)
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
            activity.drawerLayout.removeDrawerListener(toggle!!)
        } else {
            activity.drawerLayout.addDrawerListener(toggle!!)
            toggle!!.syncState()
        }
    }

}