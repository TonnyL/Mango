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
import io.github.tonnyl.mango.database.DatabaseCreator
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 *
 * Concrete implementation of a data source as a db.
 */

object AccessTokenLocalDataSource : AccessTokenDataSource {

    private var mDatabase: AppDatabase? = null

    override fun init(context: Context) {
        if (!DatabaseCreator.isDatabaseCreated()) {
            DatabaseCreator.createDb(context)
        }
    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        mDatabase?.let {
            return if (id != null) {
                Observable.just(it.accessTokenDao().query(id))
            } else {
                Observable.empty()
            }
        }

        return Observable.empty()
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        mDatabase?.let {
            Thread(Runnable {
                try {
                    mDatabase!!.beginTransaction()
                    mDatabase!!.accessTokenDao().insert(accessToken)
                    mDatabase!!.setTransactionSuccessful()
                } finally {
                    mDatabase!!.endTransaction()
                }
            }).start()
        }
    }

    override fun removeAccessToken(accessToken: AccessToken): Observable<Unit> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        return Observable.just(mDatabase?.accessTokenDao()?.delete(accessToken))
    }

}