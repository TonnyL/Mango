package io.github.tonnyl.mango.about

/**
 * Created by lizhaotailang on 2017/7/19.
 */

class AboutPresenter(view: AboutContract.View) : AboutContract.Presenter {

    private val mView = view

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }
}