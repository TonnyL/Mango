package io.github.tonnyl.mango.user

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface UserProfileContract {

    interface View : BaseView<Presenter> {

        fun showUserInfo(user: User)

    }

    interface Presenter : BasePresenter {

        fun checkFollowing()

        fun toggleFollow()

        fun getUserId(): Long

    }

}