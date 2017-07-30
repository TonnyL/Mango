package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by lizhaotailang on 2017/7/29.
 */
interface UserService {

    @GET("/v1/user")
    fun getAuthenticatedUser(): Observable<User>

    @GET("/v1/user/following/{user_id}")
    fun checkFollowing(@Path("user_id") userId: Long): Observable<Response<Body>>

    @GET("/v1/user/following/shots")
    fun listFollowingShots(@Query("per_page") per_page: Int): Observable<Response<List<Shot>>>

}