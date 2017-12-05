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

package io.github.tonnyl.mango.data.remote

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.datasource.AccessTokenDataSource
import io.github.tonnyl.mango.retrofit.AccessTokenService
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 *
 * Implementation of the data source that accessing network.
 */

object AccessTokenRemoteDataSource: AccessTokenDataSource {

    override fun init(context: Context) {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.AccessTokenRepository]
        // handle the initialization.
    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        return RetrofitClient.createService(AccessTokenService::class.java, null)
                .getAccessToken(ApiConstants.DRIBBBLE_GET_ACCESS_TOKEN_URL,
                        ApiConstants.CLIENT_ID,
                        ApiConstants.CLIENT_SECRET,
                        code!!,
                        ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI)
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.AccessTokenRepository]
        // handle the saving data to cache and database.
    }

    override fun removeAccessToken(accessToken: AccessToken): Observable<Unit> {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.AccessTokenRepository]
        // handle the removing data from cache and database.
        return Observable.empty()
    }
}