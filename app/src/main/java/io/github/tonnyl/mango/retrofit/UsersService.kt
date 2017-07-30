package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by lizhaotailang on 2017/6/24.
 */

interface UsersService {

    @PUT("/v1/users/{user_id}/follow")
    fun follow(@Path("user_id") userId: Long): Observable<Response<Body>>

    @DELETE("/v1/users/{user_id}/follow")
    fun unfollow(@Path("user_id") userId: Long): Observable<Response<Body>>

    @GET("/v1/users/{user_id}/shots")
    fun listShotsOfUser(@Path("user_id") userId: Long,
                        @Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                        @Query("page") page: Int): Observable<Response<List<Shot>>>

    @GET("/v1/users/{user_id}/likes")
    fun listLikeShotsOfUser(@Path("user_id") userId: Long,
                            @Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                            @Query("page") page: Int): Observable<Response<List<LikedShot>>>


}