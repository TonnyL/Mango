package io.github.tonnyl.mango.ui.main.shots

import io.github.tonnyl.mango.data.Shot
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

    private var mNextPageUrl: String? = null

    private val mShotsList = arrayListOf<Shot>()

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

    /**
     * There are two conditions when this function is called: first loading and refreshing.
     */
    override fun listShots() {
        mView.setLoadingIndicator(true)
        if (mType == TYPE_FOLLOWING) {
            val disposable = ShotsRepository.listFollowingShots()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mView.setLoadingIndicator(false)
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            if (it.isNotEmpty()) {
                                if (mShotsList.isNotEmpty()) {
                                    val size = mShotsList.size
                                    mShotsList.clear()
                                    mView.notifyDataAllRemoved(size)
                                    mShotsList.addAll(it)
                                    mView.notifyDataAdded(0, mShotsList.size)
                                } else {
                                    mShotsList.addAll(it)
                                    mView.showResults(mShotsList)
                                }
                            } else {
                                mView.setEmptyContentVisibility(it.isEmpty())
                            }

                        }
                    }, {
                        mView.setEmptyContentVisibility(true)
                        mView.setLoadingIndicator(false)
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        } else {
            val disposable = ShotsRepository.listShots(mType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mView.setLoadingIndicator(false)
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            if (it.isNotEmpty()) {
                                if (mShotsList.isNotEmpty()) {
                                    val size = mShotsList.size
                                    mShotsList.clear()
                                    mView.notifyDataAllRemoved(size)
                                    mShotsList.addAll(it)
                                    mView.notifyDataAdded(0, mShotsList.size)
                                } else {
                                    mShotsList.addAll(it)
                                    mView.showResults(mShotsList)
                                }
                            } else {
                                mView.setEmptyContentVisibility(it.isEmpty())
                            }

                        }
                    }, {
                        mView.setLoadingIndicator(false)
                        mView.setEmptyContentVisibility(true)
                        it.printStackTrace()
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
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mShotsList.size
                            mShotsList.addAll(it)
                            mView.notifyDataAdded(size, it.size)
                        }
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

}