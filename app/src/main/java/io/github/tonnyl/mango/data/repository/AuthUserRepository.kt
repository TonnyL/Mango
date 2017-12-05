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
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.AuthUserDataSource
import io.github.tonnyl.mango.data.local.AuthUserLocalDataSource
import io.github.tonnyl.mango.data.remote.AuthUserRemoteDataSource
import io.reactivex.Observable


/**
 * Created by lizhaotailang on 2017/6/27.
 */

object AuthUserRepository : AuthUserDataSource {

    private var mCachedUsers: MutableMap<Long, User> = linkedMapOf()

    override fun init(context: Context) {
        AuthUserLocalDataSource.init(context)
    }

    override fun getAuthenticatedUser(userId: Long?): Observable<User> {
        userId?.let {
            mCachedUsers[userId]?.let {
                return Observable.just(it)
            }
            return AuthUserLocalDataSource
                    .getAuthenticatedUser(userId)
                    .flatMap {
                        Observable.just(it)
                                .doOnNext {
                                    mCachedUsers.put(userId, it)
                                }
                    }
        } ?: run {
            return AuthUserRemoteDataSource.getAuthenticatedUser(userId)
                    .flatMap {
                        Observable.just(it)
                                .doOnNext {
                                    mCachedUsers.put(it.id, it)
                                    AuthUserLocalDataSource.saveAuthenticatedUser(it)
                                }
                    }
        }
    }

    override fun saveAuthenticatedUser(user: User) {
        AuthUserLocalDataSource.saveAuthenticatedUser(user)
    }

    override fun updateAuthenticatedUser(user: User) {
        AuthUserLocalDataSource.updateAuthenticatedUser(user)
    }

    override fun deleteAuthenticatedUser(user: User): Observable<Unit> {
        return AuthUserLocalDataSource.deleteAuthenticatedUser(user)
    }

    override fun refreshAuthenticatedUser(): Observable<User> {
        return AuthUserRemoteDataSource.refreshAuthenticatedUser()
                .flatMap {
                    Observable.just(it)
                            .doOnNext { user ->
                                if (mCachedUsers.keys.contains(user.id)) {
                                    mCachedUsers[user.id] = it
                                } else {
                                    mCachedUsers.put(it.id, it)
                                }
                                AuthUserLocalDataSource.updateAuthenticatedUser(it)
                            }
                }
    }

}