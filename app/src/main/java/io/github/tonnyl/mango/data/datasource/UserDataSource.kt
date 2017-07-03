package io.github.tonnyl.mango.data.datasource

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 */
interface UserDataSource {

    fun init(context: Context)

    fun getAuthenticatedUser(id: Long?): Observable<User>

    fun getAllAuthenticatedUsers(): Observable<List<User>>

    fun saveAuthenticatedUser(user: User)

    fun updateAuthenticatedUser(user: User)

    fun deleteAuthenticatedUser(user: User)

    fun getUser(id: Long): Observable<User>

}