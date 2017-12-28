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
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.datasource.AccessTokenDataSource
import io.github.tonnyl.mango.database.AppDatabase
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 *
 * Concrete implementation of a data source as a db.
 */

object AccessTokenLocalDataSource : AccessTokenDataSource {

    private lateinit var mDatabase: AppDatabase

    override fun init(context: Context) {
        mDatabase = AppDatabase.getInstance(context)
    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        return if (id != null) {
            Observable.just(mDatabase.accessTokenDao().query(id))
        } else {
            Observable.empty()
        }
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        Thread(Runnable {
            with(mDatabase) {
                try {
                    beginTransaction()
                    accessTokenDao().insert(accessToken)
                    setTransactionSuccessful()
                } finally {
                    endTransaction()
                }
            }
        }).start()
    }

    override fun removeAccessToken(accessToken: AccessToken): Observable<Unit> {
        return Observable.just(mDatabase.accessTokenDao().delete(accessToken))
    }

}