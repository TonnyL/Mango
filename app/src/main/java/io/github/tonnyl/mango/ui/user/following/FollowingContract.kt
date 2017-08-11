package io.github.tonnyl.mango.ui.user.following

import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/29.
 *
 * This specifies the contract between the view and the presenter.
 */
interface FollowingContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun setEmptyViewVisibility(visible: Boolean)

        fun showNetworkError()

        fun showFollowings(followings: List<Followee>)

        fun notifyDataAllRemoved(size: Int)

        fun notifyDataAdded(startPosition: Int, size: Int)

    }

    interface Presenter : BasePresenter {

        fun loadFollowing()

        fun loadMoreFollowing()

    }

}