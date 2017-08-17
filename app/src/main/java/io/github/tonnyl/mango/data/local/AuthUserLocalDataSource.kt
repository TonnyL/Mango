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