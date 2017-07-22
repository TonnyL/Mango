package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by lizhaotailang on 2017/6/24.
 */

interface UserService {

    @GET("/v1/user")
    fun getAuthenticatedUser(): Observable<User>

    @GET("/v1/user/following/{user_id}")
    fun checkFollowing(@Path("user_id") userId: Long): Observable<Response<Body>>

    @PUT("/v1/users/{user_id}/follow")
    fun follow(@Path("user_id") userId: Long): Observable<Response<Body>>

    @DELETE("/v1/users/{user_id}/follow")
    fun unfollow(@Path("user_id") userId: Long): Observable<Response<Body>>

}