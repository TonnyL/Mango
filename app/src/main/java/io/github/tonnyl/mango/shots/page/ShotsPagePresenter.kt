package io.github.tonnyl.mango.shots.page

import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class ShotsPagePresenter(view: ShotsPageContract.View, type: Int) : ShotsPageContract.Presenter {

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable
    private val mType = type

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    companion object {
        const val TYPE_POPULAR = 0x00
        const val TYPE_FOLLOWING = 0x01
        const val TYPE_RECENT = 0x02
        const val TYPE_DEBUTS = 0x03
    }

    override fun subscribe() {
        listShots()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun listShots() {
        mView.setLoadingIndicator(true)
        if (mType == TYPE_FOLLOWING) {
            ShotsRepository.listFollowingShots()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        response.body()?.let {
                            if (mView.isActive()) {
                                mView.showResults(it.toMutableList())
                                mView.setLoadingIndicator(false)
                            }
                        }
                    }, { error ->
                        if (mView.isActive()) {
                            error.message?.let { mView.showMessage(it) }
                            mView.setLoadingIndicator(false)
                        }
                    })
        } else {
            ShotsRepository.listShots(mType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        response.body()?.let {
                            if (mView.isActive()) {
                                mView.showResults(it.toMutableList())
                                mView.setLoadingIndicator(false)
                            }
                        }
                    }, { error ->
                        if (mView.isActive()) {
                            error.message?.let { mView.showMessage(it) }
                            mView.setLoadingIndicator(false)
                        }
                    })
        }
    }

}