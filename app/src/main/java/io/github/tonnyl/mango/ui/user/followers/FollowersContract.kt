package io.github.tonnyl.mango.ui.user.followers

import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/29.
 *
 * This specifies the contract between the view and the presenter.
 */
interface FollowersContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun setEmptyViewVisibility(visible: Boolean)

        fun showNetworkError()

        fun showFollowers(followers: List<Follower>)

        fun notifyDataAllRemoved(size: Int)

        fun notifyDataAdded(startPosition: Int, size: Int)

    }

    interface Presenter : BasePresenter {

        fun loadFollowers()

        fun loadMoreFollowers()

    }

}