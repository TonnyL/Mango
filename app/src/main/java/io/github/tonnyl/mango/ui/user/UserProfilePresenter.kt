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

package io.github.tonnyl.mango.ui.user

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.UserProfileFragment],
 * retrieves the data and update the ui as required.
 */

class UserProfilePresenter(view: UserProfileContract.View, user: User) : UserProfileContract.Presenter {

    private var mView: UserProfileContract.View = view
    private var mCompositeDisposable: CompositeDisposable
    private var mFollowingChecked = false
    private var mIsFollowing = false

    private var mUser = user

    companion object {
        @JvmField
        val EXTRA_USER = "EXTRA_USER"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.showUserInfo(mUser)
        if (AccessTokenManager.accessToken?.id == mUser.id) {
            mView.setFollowable(false)
            getUpdatedUserInfo()
        } else {
            checkFollowing()
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun checkFollowing() {
        val disposable = UserRepository.checkFollowing(mUser.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code() == 204) {
                        mIsFollowing = true
                        mView.setFollowing(true)
                    } else if (it.code() == 404) {
                        mIsFollowing = false
                        mView.setFollowing(false)
                    }
                    mFollowingChecked = true
                    mView.setFollowable(true)
                }, {
                    mFollowingChecked = false
                    mView.setFollowable(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun toggleFollow() {
        if (mIsFollowing) {
            UserRepository.unfollow(mUser.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mIsFollowing = false
                        mView.setFollowing(false)
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
        } else {
            UserRepository.follow(mUser.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mIsFollowing = true
                        mView.setFollowing(true)
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
        }
    }

    override fun getUser(): User {
        return mUser
    }

    private fun getUpdatedUserInfo() {
        val disposable = AuthUserRepository.refreshAuthenticatedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mUser = it
                    mView.showUserInfo(it)
                }, {
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

}