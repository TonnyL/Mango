package io.github.tonnyl.mango.data.repository

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.UserDataSource
import io.github.tonnyl.mango.data.local.UserLocalDataSource
import io.github.tonnyl.mango.data.remote.UserRemoteDataSource
import io.reactivex.Observable


/**
 * Created by lizhaotailang on 2017/6/27.
 */

object UserRepository: UserDataSource {

    private var mCachedAuthenticatedUsers: HashMap<Long, User>? = null

    override fun init(context: Context) {
        UserLocalDataSource.init(context)
    }

    override fun getAuthenticatedUser(id: Long?): Observable<User> {
        if (id == null) {
            return UserRemoteDataSource.getAuthenticatedUser(null)
        }

        return UserLocalDataSource.getAuthenticatedUser(id)
    }

    override fun getAllAuthenticatedUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAuthenticatedUser(user: User) {
        UserLocalDataSource.saveAuthenticatedUser(user)
    }

    override fun updateAuthenticatedUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAuthenticatedUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(id: Long): Observable<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}