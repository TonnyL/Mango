package io.github.tonnyl.mango.user.followers

import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/29.
 */
interface FollowersContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showFollowers(followers: MutableList<Follower>)

    }

    interface Presenter : BasePresenter

}