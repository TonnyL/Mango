package io.github.tonnyl.mango.data.datasource

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 *
 * Main entry point for accessing user data.
 */
interface AuthUserDataSource {

    fun init(context: Context)

    fun getAuthenticatedUser(userId: Long?): Observable<User>

    fun saveAuthenticatedUser(user: User)

    fun updateAuthenticatedUser(user: User)

    fun deleteAuthenticatedUser(user: User): Observable<Unit>

    fun refreshAuthenticatedUser(): Observable<User>

}