package io.github.tonnyl.mango.user

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lizhaotailang on 2017/6/28.
 */
class UserPresenter(view: UserContract.View): UserContract.Presenter {

    private var mView: UserContract.View = view
    private var mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}