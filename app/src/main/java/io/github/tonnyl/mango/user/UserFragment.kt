package io.github.tonnyl.mango.user

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class UserFragment: Fragment(), UserContract.View {

    private lateinit var mPresenter: UserContract.Presenter

    companion object {
        fun getInstance(): UserFragment {
            return UserFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: UserContract.Presenter) {
        mPresenter = presenter
    }

    override fun show(user: User) {

    }

}