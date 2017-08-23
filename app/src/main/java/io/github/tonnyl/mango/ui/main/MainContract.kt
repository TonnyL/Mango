package io.github.tonnyl.mango.ui.main

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {

    interface View: BaseView<Presenter> {

        fun initViews()

        fun showAuthUserInfo(user: User)

        fun disableShortcuts()

        fun navigateToLogin()

    }

    interface Presenter: BasePresenter {

        fun fetchUser()

        fun logoutUser()

        fun getUser(): User?

    }

}