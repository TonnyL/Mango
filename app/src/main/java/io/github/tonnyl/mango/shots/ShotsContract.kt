package io.github.tonnyl.mango.shots

import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface ShotsContract {

    interface View: BaseView<Presenter> {

        fun isActive(): Boolean

        fun initViews()

    }

    interface Presenter: BasePresenter {

    }

}