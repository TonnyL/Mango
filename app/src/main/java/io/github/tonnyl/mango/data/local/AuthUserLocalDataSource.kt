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

package io.github.tonnyl.mango.data.local

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.AuthUserDataSource
import io.github.tonnyl.mango.database.AppDatabase
import io.github.tonnyl.mango.database.DatabaseCreator
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Concrete implementation of a data source as a db.
 */

object AuthUserLocalDataSource : AuthUserDataSource {

    private var mDatabase: AppDatabase? = null

    override fun init(context: Context) {
        if (!DatabaseCreator.isDatabaseCreated()) {
            DatabaseCreator.createDb(context)
        }
        mDatabase = DatabaseCreator.getDatabase()
    }

    override fun getAuthenticatedUser(userId: Long?): Observable<User> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        mDatabase?.let {
            return if (userId != null) {
                it.userDao().query(userId).toObservable()
            } else {
                Observable.empty()
            }
        }
        return Observable.empty()
    }

    override fun saveAuthenticatedUser(user: User) {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        mDatabase?.let {
            Thread(Runnable {
                try {
                    it.beginTransaction()
                    it.userDao().insert(user)
                    it.setTransactionSuccessful()
                } finally {
                    it.endTransaction()
                }
            }).start()
        }
    }

    override fun updateAuthenticatedUser(user: User) {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }
        mDatabase?.userDao()?.update(user)
    }

    override fun deleteAuthenticatedUser(user: User): Observable<Unit> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }
        return Observable.just(mDatabase?.userDao()?.delete(user))
    }

    override fun refreshAuthenticatedUser(): Observable<User> {
        // Not required for the remote data source because the [io.github.tonnyl.mango.data.repository.UserRepository]
        // handles removing data from cache and database.
        return Observable.empty()
    }

}