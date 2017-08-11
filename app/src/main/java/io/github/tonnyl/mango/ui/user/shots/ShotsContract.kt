package io.github.tonnyl.mango.ui.user.shots

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * This specifies the contract between the view and the presenter.
 */

interface ShotsContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showShots(shots: List<Shot>)

        fun notifyDataAllRemoved(size: Int)

        fun notifyDataAdded(startPosition: Int, size: Int)

        fun setEmptyViewVisibility(visible: Boolean)

        fun showNetworkError()

    }

    interface Presenter : BasePresenter {

        fun loadShots()

        fun loadShotsOfNextPage()

    }

}