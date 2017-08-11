package io.github.tonnyl.mango.ui.user.likeshots

import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.github.tonnyl.mango.util.PageLinks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.likeshots.LikedShotsFragment],
 * retrieves the data and update the ui as required.
 */
class LikedShotsPresenter(view: LikedShotsContract.View, userId: Long) : LikedShotsContract.Presenter {

    private val mView = view
    private val mUserId = userId
    private val mCompositeDisposable: CompositeDisposable

    private var mNextPageUrl: String? = null
    private val mCachedLikedShots = arrayListOf<LikedShot>()

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadLikedShots()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadLikedShots() {
        mView.setLoadingIndicator(true)
        val disposable = ShotsRepository.listLikedShotsOfUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.setLoadingIndicator(false)
                    mNextPageUrl = PageLinks(response).next

                    response.body()?.let {
                        if (it.isNotEmpty()) {
                            if (mCachedLikedShots.isNotEmpty()) {
                                val size = mCachedLikedShots.size
                                mCachedLikedShots.clear()
                                mView.notifyDataAllRemoved(size)
                                mCachedLikedShots.addAll(it)
                                mView.notifyDataAdded(0, mCachedLikedShots.size)
                            } else {
                                mCachedLikedShots.addAll(it)
                                mView.showLikedShots(mCachedLikedShots)
                            }
                        } else {
                            mView.setEmptyViewVisibility(it.isEmpty())
                        }
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadMoreLikedShots() {
        mNextPageUrl?.let {
            val disposable = ShotsRepository.listLikedShotOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mCachedLikedShots.size
                            mCachedLikedShots.addAll(it)
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