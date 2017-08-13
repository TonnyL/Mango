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
            if (id != null) {
                return Observable.just(it.accessTokenDao().query(id))
            } else {
                return Observable.empty()
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