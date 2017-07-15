package io.github.tonnyl.mango.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.user.UserActivity
import io.github.tonnyl.mango.util.FrescoLoader

import kotlinx.android.synthetic.main.fragment_shots.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class MainFragment : Fragment(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_shots, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

        user_info_layout.setOnClickListener{
            context.startActivity<UserActivity>()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.action_logout) {

        } else if (id == R.id.action_about) {

        }
        return true
    }

    override fun initViews() {
        (activity as MainActivity).setSupportActionBar(toolbar)

        view_pager.adapter = MainPagerAdapter(context, childFragmentManager)
        view_pager.offscreenPageLimit = 4

        tab_layout.setupWithViewPager(view_pager)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }

    override fun showAuthUserInfo(user: User) {
        user_name.text = user.name
        FrescoLoader.loadAvatar(avatar_drawee, user.avatarUrl)
    }

}