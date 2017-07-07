package io.github.tonnyl.mango.shot

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.repository.ShotRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class ShotPresenter(view: ShotContract.View) : ShotContract.Presenter {

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable
    private var mIsLikeChecked = false
    private var mIsLike = false
    private var mShotId: Long = 0L
    private var mShot: Shot? = null

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        ShotRepository.getShot(mShotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.body() != null) {
                        mShot = response.body()
                        mShot?.let { mView.show(it) }
                    }
                })

        ShotRepository.checkLikeOfShot(mShotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mIsLikeChecked = true
                    mIsLike = (response.body() != null)
                    mView.setLikeStatus(mIsLike)
                })
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun setShotId(shotId: Long) {
        mShotId = shotId
    }

    override fun toggleLike() {
        if (mShotId != 0L && mShot != null) {
            if (mIsLike) {
                ShotRepository.unlikeShot(mShotId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            mShot?.let {
                                it.likesCount--
                                mView.updateLikeCount(it.likesCount)
                            }
                            mView.setLikeStatus(false)
                            mIsLike = !mIsLike
                        }, { error ->
                            mView.showMessage(error.message)
                        })
            } else {
                ShotRepository.likeShot(mShotId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            mShot?.let {
                                it.likesCount++
                                mView.updateLikeCount(it.likesCount)
                            }
                            mView.setLikeStatus(true)
                            mIsLike = !mIsLike
                        }, { error ->
                            mView.showMessage(error.message)
                        })
            }
        }
    }

    override fun navigateToUserProfile() {
        mShot?.let { mView.navigateToUserProfile(it.id) }
    }

    override fun navigateToComments() {
        if (mShotId != 0L) {
            mView.navigateToComments(mShotId)
        }
    }

    override fun navigateToBuckets() {
        if (mShotId != 0L) {
            mView.navigateToBuckets(mShotId)
        }
    }

    override fun navigateToLikes() {
        if (mShotId != 0L) {
            mView.navigateToLikes(mShotId)
        }
    }

    override fun navigateToAttachments() {
        if (mShotId != 0L) {
            mView.navigateToAttachments(mShotId)
        }
    }

}