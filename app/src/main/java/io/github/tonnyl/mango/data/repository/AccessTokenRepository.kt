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

package io.github.tonnyl.mango.data.repository

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.datasource.AccessTokenDataSource
import io.github.tonnyl.mango.data.local.AccessTokenLocalDataSource
import io.github.tonnyl.mango.data.remote.AccessTokenRemoteDataSource
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 */

object AccessTokenRepository : AccessTokenDataSource {

    override fun init(context: Context) {
        AccessTokenLocalDataSource.init(context)
    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        if (code != null) {
            return AccessTokenRemoteDataSource.getAccessToken(id, code)
                    .flatMap { accessToken ->
                        Observable.just(accessToken)
                                .doOnNext {
                                    AccessTokenManager.accessToken = accessToken
                                }
                    }
        }

        return AccessTokenLocalDataSource.getAccessToken(id, code)
                .flatMap { accessToken ->
                    Observable.just(accessToken)
                            .doOnNext {
                                AccessTokenManager.accessToken = accessToken
                            }
                }
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        AccessTokenLocalDataSource.saveAccessToken(accessToken)
    }

    override fun removeAccessToken(accessToken: AccessToken): Observable<Unit> {
        return AccessTokenLocalDataSource.removeAccessToken(accessToken)
    }
}