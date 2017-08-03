package io.github.tonnyl.mango.data.datasource

import android.content.Context
import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.Follower
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

    fun deleteAuthenticatedUser(user: User): Observable<Unit>

    fun checkFollowing(userId: Long): Observable<Response<Body>>

    fun follow(userId: Long): Observable<Response<Body>>

    fun unfollow(userId: Long): Observable<Response<Body>>

    fun listFollowerOfUser(userId: Long): Observable<Response<List<Follower>>>

    fun listFolloweeOfUser(userId: Long): Observable<Response<List<Followee>>>

}