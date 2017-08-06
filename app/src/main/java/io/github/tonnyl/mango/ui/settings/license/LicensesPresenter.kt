package io.github.tonnyl.mango.ui.settings.license

/**
 * Created by lizhaotailang on 2017/7/21.
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