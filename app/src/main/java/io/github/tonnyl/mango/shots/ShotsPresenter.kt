package io.github.tonnyl.mango.shots

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class ShotsPresenter(view: ShotsContract.View) : ShotsContract.Presenter {

    private val mView: ShotsContract.View = view
    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {

    }
}