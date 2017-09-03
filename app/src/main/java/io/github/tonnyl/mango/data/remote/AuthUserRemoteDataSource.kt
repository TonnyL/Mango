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
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.AuthUserDataSource
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.UserService
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Implementation of data source that accessing network.
 */

object AuthUserRemoteDataSource : AuthUserDataSource {

    private var mUserService: UserService = RetrofitClient.createService(UserService::class.java, AccessTokenManager.accessToken)

    override fun init(context: Context) {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.UserRepository]
        // handles the initialization.
    }

    override fun getAuthenticatedUser(userId: Long?): Observable<User> {
        return mUserService.getAuthenticatedUser()
    }

    override fun saveAuthenticatedUser(user: User) {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.UserRepository]
        // handles saving data to cache and database.
    }

    override fun updateAuthenticatedUser(user: User) {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.UserRepository]
        // handles updating data of cache and database.
    }

    override fun deleteAuthenticatedUser(user: User): Observable<Unit> {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.UserRepository]
        // handles removing data from cache and database.
        return Observable.empty()
    }

    override fun refreshAuthenticatedUser(): Observable<User> {
        return mUserService.getAuthenticatedUser()
    }

}