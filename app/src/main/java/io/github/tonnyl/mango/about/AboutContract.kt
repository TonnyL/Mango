package io.github.tonnyl.mango.about

import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/19.
 */

interface AboutContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}