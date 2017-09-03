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

package io.github.tonnyl.mango.ui.user.shots

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.github.tonnyl.mango.util.PageLinks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.shots.ShotsFragment],
 * retrieves the data and update the ui as required.
 */
class ShotsPresenter(view: ShotsContract.View, user: User) : ShotsContract.Presenter {

    private val mView = view
    private val mUser = user
    private val mCompositeDisposable: CompositeDisposable

    private var mNextPageUrl: String? = null

    private val mCachedShots = arrayListOf<Shot>()

    companion object {
        @JvmField
        val EXTRA_USER = "EXTRA_USER"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadShots()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadShots() {
        mView.setLoadingIndicator(true)
        val disposable = ShotsRepository.listShotsOfUser(mUser.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.setLoadingIndicator(false)
                    mNextPageUrl = PageLinks(response).next

                    response.body()?.let {
                        mView.setEmptyViewVisibility(it.isEmpty())
                        if (it.isNotEmpty()) {
                            for (shot in it) {
                                shot.user = mUser
                            }
                            if (mCachedShots.isNotEmpty()) {
                                val size = mCachedShots.size
                                mCachedShots.clear()
                                mView.notifyDataAllRemoved(size)
                                mCachedShots.addAll(it)
                                mView.notifyDataAdded(0, it.size)
                            } else {
                                mCachedShots.addAll(it)
                                mView.showShots(mCachedShots)
                            }
                        }
                    }
                }, {
                    mView.setEmptyViewVisibility(true)
                    mView.setLoadingIndicator(false)
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadShotsOfNextPage() {
        mNextPageUrl?.let {
            val disposable = ShotsRepository.listShotsOfUserOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mCachedShots.size
                            mCachedShots.addAll(it)
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