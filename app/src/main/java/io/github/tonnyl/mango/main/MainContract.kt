package io.github.tonnyl.mango.main

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface MainContract {

    interface View: BaseView<Presenter> {

        fun initViews()

        fun showAuthUserInfo(user: User)

    }

    interface Presenter: BasePresenter {

        fun fetchUser()

    }

}