package io.github.tonnyl.mango.ui.main.shots

import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.github.tonnyl.mango.util.PageLinks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/29.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.main.shots.ShotsPageFragment],
 * retrieves the data and update the ui as required.
 */

class ShotsPagePresenter(view: ShotsPageContract.View, type: Int) : ShotsPageContract.Presenter {

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable
    private val mType = type

    private var mIsFirstLoad = true
    private var mNextPageUrl: String? = null

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    companion object {
        @JvmField
        val TYPE_POPULAR = 0x00
        @JvmField
        val TYPE_FOLLOWING = 0x01
        @JvmField
        val TYPE_RECENT = 0x02
        @JvmField
        val TYPE_DEBUTS = 0x03
    }

    override fun subscribe() {
        listShots()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun listShots() {
        if (mIsFirstLoad) {
            mView.setLoadingIndicator(true)
            mIsFirstLoad = false
        }

        if (mType == TYPE_FOLLOWING) {
            val disposable = ShotsRepository.listFollowingShots()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mView.setLoadingIndicator(false)
                        response.body()?.let {
                            mView.showResults(it.toMutableList())
                        }
                        // Set next page url
                        mNextPageUrl = PageLinks(response).next
                    }, { error ->
                        error.message?.let { mView.showMessage(it) }
                        mView.setLoadingIndicator(false)
                    })
            mCompositeDisposable.add(disposable)
        } else {
            val disposable = ShotsRepository.listShots(mType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mView.setLoadingIndicator(false)
                        response.body()?.let {
                            mView.showResults(it.toMutableList())
                        }
                    }, { error ->
                        error.message?.let { mView.showMessage(it) }
                        mView.setLoadingIndicator(false)
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun listMoreShots() {
        mNextPageUrl?.let {
            val disposable = ShotsRepository.listShotsOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mView.setLoadingIndicator(false)
                        response.body()?.let {
                            mView.showResults(it.toMutableList())
                        }
                    }, { error ->
                        error.message?.let { mView.showMessage(it) }
                        mView.setLoadingIndicator(false)
                    })
            mCompositeDisposable.add(disposable)
        }
    }

}