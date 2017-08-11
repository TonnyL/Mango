package io.github.tonnyl.mango.ui.main.shots

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/29.
 *
 * This specifies the contract between the view and the presenter.
 */

interface ShotsPageContract {

    interface View: BaseView<Presenter> {

        fun initViews()

        fun setLoadingIndicator(loading: Boolean)

        fun showResults(results: List<Shot>)

        fun notifyDataAllRemoved(size: Int)

        fun notifyDataAdded(startPosition: Int, size: Int)

        fun showNetworkError()

        fun setEmptyContentVisibility(visible: Boolean)

    }

    interface Presenter: BasePresenter {

        fun listShots()

        fun listMoreShots()

    }

}