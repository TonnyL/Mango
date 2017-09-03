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

package io.github.tonnyl.mango.ui.main

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.AccessTokenRepository
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Listens the user action from the ui [io.github.tonnyl.mango.ui.main.MainFragment],
 * retrieves the data and update the ui as required.
 */

class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    private var mView: MainContract.View = view
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mUser: User? = null

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        fetchUser()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchUser() {
        val disposable = AuthUserRepository.getAuthenticatedUser(AccessTokenManager.accessToken?.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mUser = response
                    mView.showAuthUserInfo(response)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun logoutUser() {
        AccessTokenManager.accessToken?.let { token ->
            val disposable = AuthUserRepository.getAuthenticatedUser(token.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        val obs1: Observable<Unit> = AuthUserRepository.deleteAuthenticatedUser(it)
                                .subscribeOn(Schedulers.io())

                        val obs2: Observable<Unit> = AccessTokenRepository.removeAccessToken(token)
                                .subscribeOn(Schedulers.io())

                        Observable.zip(obs1, obs2, BiFunction<Unit, Unit, Unit> { _, _ ->
                            Observable.just(Unit)
                        }).observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    mView.disableShortcuts()

                                    mView.navigateToLogin()
                                }, {
                                    it.printStackTrace()
                                })
                    }, {
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun getUser(): User? {
        return mUser
    }

}