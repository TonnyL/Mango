package io.github.tonnyl.mango.user

import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView
import io.github.tonnyl.mango.data.User

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface UserContract {

    interface View : BaseView<Presenter> {

        fun show(user: User)

    }

    interface Presenter : BasePresenter {

        fun checkFollowing()

        fun toggleFollow()

    }

}