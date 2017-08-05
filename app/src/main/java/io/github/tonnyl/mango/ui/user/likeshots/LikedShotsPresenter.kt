package io.github.tonnyl.mango.ui.user.likeshots

import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class LikedShotsPresenter(view: LikedShotsContract.View, userId: Long) : LikedShotsContract.Presenter {

    private val mView = view
    private val mUserId = userId
    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        val disposable = ShotsRepository.listLikeShotsOfUser(mUserId, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        mView.showShots(it.toMutableList())
                    }
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}