package io.github.tonnyl.mango.ui.main.shots

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/29.
 */

interface ShotsPageContract {

    interface View: BaseView<Presenter> {

        fun initViews()

        fun setLoadingIndicator(loading: Boolean)

        fun showResults(results: MutableList<Shot>)

        fun showMessage(message: String)

    }

    interface Presenter: BasePresenter {

        fun listShots()

    }

}