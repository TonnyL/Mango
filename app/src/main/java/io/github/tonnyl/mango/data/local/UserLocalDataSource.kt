package io.github.tonnyl.mango.data.local

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.UserDataSource
import io.github.tonnyl.mango.database.AppDatabase
import io.github.tonnyl.mango.database.DatabaseCreator
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@Singleton
object UserLocalDataSource: UserDataSource {

    private var mDatabase: AppDatabase? = null

    override fun init(context: Context) {
        if (!DatabaseCreator.isDatabaseCreated()) {
            DatabaseCreator.createDb(context)
        }
    }

    override fun getAuthenticatedUser(id: Long?): Observable<User> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }
        return mDatabase!!.userDao().query(id!!).toObservable()
    }

    override fun getAllAuthenticatedUsers(): Observable<List<User>> {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }
        return mDatabase!!.userDao().queryAll().toObservable()
    }

    override fun saveAuthenticatedUser(user: User) {
        if (mDatabase == null) {
            mDatabase = DatabaseCreator.getDatabase()
        }

        Thread(Runnable {
            try {
                mDatabase?.beginTransaction()
                mDatabase?.userDao()?.insert(user)
                mDatabase?.setTransactionSuccessful()
            } finally {
                mDatabase?.endTransaction()
            }
        }).start()
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
        mDatabase?.userDao()?.delete(user)
        return Observable.empty()
    }

    override fun checkFollowing(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun follow(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfollow(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}