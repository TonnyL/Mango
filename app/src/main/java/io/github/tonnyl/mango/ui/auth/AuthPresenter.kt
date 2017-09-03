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

package io.github.tonnyl.mango.ui.auth

import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.repository.AccessTokenRepository
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.auth.AuthFragment],
 * retrieves the data and update the ui as required.
 */

class AuthPresenter(view: AuthContract.View) : AuthContract.Presenter {

    private val mView: AuthContract.View = view
    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun requestAccessToken(code: String) {
        mCompositeDisposable.clear()
        val disposable = AccessTokenRepository.getAccessToken(null, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    if (token.accessToken.isNullOrEmpty()) {
                        if (mView.isActive()) {
                            mView.setLoginIndicator(false)
                            mView.showMessage(R.string.request_refresh_token_failed)
                        }
                    } else {
                        // Update the access token of AccountManager
                        AccessTokenManager.accessToken = token

                        // Save the access token to database
                        AccessTokenRepository.saveAccessToken(token)

                        requestUserInfo()
                    }
                }, { error ->
                    if (mView.isActive()) {
                        mView.setLoginIndicator(false)
                        error.message?.let {
                            mView.showMessage(it)
                        }
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    private fun requestUserInfo() {
        val disposable = AuthUserRepository.getAuthenticatedUser(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    if (user.id != 0L) {
                        // Update the user id in access token
                        AccessTokenManager.accessToken?.let { it.id = user.id }

                        // Save the user info to database
                        AuthUserRepository.saveAuthenticatedUser(user)

                        AccessTokenManager.accessToken?.let { mView.updateLoginStatus(it) }
                    }
                }, { error ->
                    if (mView.isActive()) {
                        mView.setLoginIndicator(false)
                        error.message?.let {
                            mView.showMessage(it)
                        }
                    }
                })
        mCompositeDisposable.add(disposable)
    }

}