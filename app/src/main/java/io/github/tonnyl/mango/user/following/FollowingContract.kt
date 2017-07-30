package io.github.tonnyl.mango.user.following

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/29.
 */
interface FollowingContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showFollowings(followings: MutableList<User>)

    }

    interface Presenter : BasePresenter

}