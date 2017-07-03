package io.github.tonnyl.mango.shots.page

import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView
import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/6/29.
 */

interface ShotsPageContract {

    interface View: BaseView<Presenter> {

        fun initViews()

        fun isActive(): Boolean

        fun setLoadingIndicator(loading: Boolean)

        fun showResults(results: MutableList<Shot>)

        fun showMessage(message: String)

    }

    interface Presenter: BasePresenter {

        fun listShots()

    }

}