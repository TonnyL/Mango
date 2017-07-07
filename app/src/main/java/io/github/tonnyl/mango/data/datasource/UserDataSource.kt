package io.github.tonnyl.mango.data.datasource

import android.content.Context
import io.github.tonnyl.mango.data.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body

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

    fun getUser(id: Long): Observable<Response<User>>

    fun checkFollowing(userId: Long): Observable<Response<Body>>

    fun follow(userId: Long): Observable<Response<Body>>

    fun unfollow(userId: Long): Observable<Response<Body>>

}