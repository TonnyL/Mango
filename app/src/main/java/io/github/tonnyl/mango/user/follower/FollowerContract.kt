package io.github.tonnyl.mango.user.follower

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/29.
 */
interface FollowerContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showFollowers(followers: MutableList<User>)

    }

    interface Presenter : BasePresenter

}