/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.ui.shot

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.repository.ShotRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.shot.ShotFragment],
 * retrieves the data and update the ui as required.
 */

class ShotPresenter(view: ShotContract.View, shotId: Long) : ShotContract.Presenter {

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable
    private var mIsLikeChecked = false
    private var mIsLike = false

    private var mIsDeepLink = true
    private var mShot: Shot? = null
    private var mShotId = shotId

    companion object {
        @JvmField
        val EXTRA_SHOT = "EXTRA_SHOT"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    constructor(view: ShotContract.View, shot: Shot) : this(view, shot.id) {
        mIsDeepLink = false
        mShot = shot
    }

    override fun subscribe() {
        if (mIsDeepLink && mShot == null) {
            val disposable = ShotRepository.getShot(mShotId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ shot ->
                        shot.body()?.let {
                            mShot = it
                            mShotId = it.id
                            mView.show(it)
                        }
                    }, {
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        } else {
            mShot?.let {
                mView.show(it)
            }
        }

        val disposable = ShotRepository.checkLike(mShotId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mIsLikeChecked = true
                    mIsLike = (response.body() != null)
                    mView.setLikeStatus(mIsLike)
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun toggleLike() {
        if (mIsLike) {
            val disposable = ShotRepository
                    .unlikeShot(mShotId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mShot?.let {
                            it.likesCount--
                            mView.updateLikeCount(it.likesCount)
                            mView.setLikeStatus(false)
                            mIsLike = !mIsLike
                        }
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        } else {
            val disposable = ShotRepository.likeShot(mShotId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mShot?.let {
                            it.likesCount++
                            mView.updateLikeCount(it.likesCount)
                            mView.setLikeStatus(true)
                            mIsLike = !mIsLike
                        }
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun navigateToUserProfile() {
        mShot?.user?.let {
            mView.navigateToUserProfile(it)
        }
    }

    override fun navigateToComments() {
        mShot?.let {
            mView.navigateToComments(it)
        }
    }

    override fun navigateToLikes() {
        mShot?.let {
            mView.navigateToLikes(it)
        }
    }

    override fun share() {
        mShot?.let {
            mView.share(it.htmlUrl)
        }
    }

    override fun openInBrowser() {
        mShot?.let {
            mView.openInBrowser(it.htmlUrl)
        }
    }

}