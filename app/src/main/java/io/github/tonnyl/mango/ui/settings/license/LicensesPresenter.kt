package io.github.tonnyl.mango.ui.settings.license

/**
 * Created by lizhaotailang on 2017/7/21.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.settings.license.LicensesFragment],
 * retrieves the data and update the ui as required.
 */

class LicensesPresenter(view: LicensesContract.View) : LicensesContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }

}