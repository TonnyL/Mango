package io.github.tonnyl.mango.ui.settings.license

import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/21.
 *
 * This specifies the contract between the view and the presenter.
 */

interface LicensesContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}