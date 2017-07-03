package io.github.tonnyl.mango.buckets

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class BucketsPresenter(view: BucketsContract.View): BucketsContract.Presenter {

    private val mView: BucketsContract.View = view
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