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

    private lateinit var mShot: Shot

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.show(mShot)
        val disposable = ShotRepository.checkLike(mShot.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mIsLikeChecked = true
                    mIsLike = (response.body() != null)
                    mView.setLikeStatus(mIsLike)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun setShot(shot: Shot) {
        mShot = shot
    }

    override fun toggleLike() {
        if (mIsLike) {

            val disposable = ShotRepository
                    .unlikeShot(mShot.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mShot.likesCount--
                        mView.updateLikeCount(mShot.likesCount)
                        mView.setLikeStatus(false)
                        mIsLike = !mIsLike
                    }, { error ->
                        mView.showMessage(error.message)
                    })
            mCompositeDisposable.add(disposable)
        } else {
            val disposable = ShotRepository.likeShot(mShot.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mShot.likesCount++
                        mView.updateLikeCount(mShot.likesCount)
                        mView.setLikeStatus(true)
                        mIsLike = !mIsLike
                    }, { error ->
                        mView.showMessage(error.message)
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun navigateToUserProfile() {
        mShot.user?.let {
            mView.navigateToUserProfile(it)
        }
    }

    override fun navigateToComments() {
        mView.navigateToComments(mShot.id)
    }

    override fun navigateToLikes() {
        mView.navigateToLikes(mShot.id)
    }

}