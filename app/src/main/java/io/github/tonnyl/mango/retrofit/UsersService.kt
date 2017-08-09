package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * [retrofit2.Retrofit] service all about [io.github.tonnyl.mango.data.User]s. Works with [RetrofitClient].
 */

interface UsersService {

    /**
     * Follow a user.
     * Following a user requires the user to be authenticated with the `write` scope.
     *
     * @param userId The user id.
     * @return The follow result. If succeed, the status code of response of will be 204 (no content).
     *         Or you will get some errors. The following errors are possible, and will be on the `base` attribute:
     *         You cannot follow yourself.
     *         You have been blocked from following this member at their request.
     *         You have reached the maximum number of follows allowed.
     */
    @PUT("/v1/users/{user_id}/follow")
    fun follow(@Path("user_id") userId: Long): Observable<Response<Body>>

    /**
     * Unfollow a user.
     * Like [follow] method above, unfollowing a user requires the user to be authenticated with the `write` scope.
     *
     * @param userId The user id.
     * @return The unfollow result. If succeed, the status code of response will be 204 (no content).
     */
    @DELETE("/v1/users/{user_id}/follow")
    fun unfollow(@Path("user_id") userId: Long): Observable<Response<Body>>

    /**
     * List shots for a user.
     *
     * @param userId The user id.
     * @param perPage The amount of shots per page.
     * @param page Which page you want to get.
     * @return The list of [Shot] results.
     */
    @GET("/v1/users/{user_id}/shots")
    fun listShotsOfUser(@Path("user_id") userId: Long,
                        @Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                        @Query("page") page: Int): Observable<Response<List<Shot>>>

    /**
     * List shots that a user likes.
     *
     * @param userId The user id.
     * @param perPage The amount of shots per page.
     * @return The list of [Shot] results.
     */
    @GET("/v1/users/{user_id}/likes")
    fun listLikeShotsOfUser(@Path("user_id") userId: Long,
                            @Query("per_page") perPage: Int = ApiConstants.PER_PAGE,
                            @Query("page") page: Int): Observable<Response<List<LikedShot>>>

    /**
     * List the followers of a user.
     *
     * @param userId The user id.
     * @param per_page The amount of [Follower] results per page.
     * @return The list of [Follower] results.
     */
    @GET("/v1/users/{user_id}/followers")
    fun listFollowerOfUser(@Path("user_id") userId: Long,
                           @Query("per_page") per_page: Int): Observable<Response<List<Follower>>>

    /**
     * List the followees (who is followed) by a user.
     *
     * @param userId The user id.
     * @param per_page The amount of [Followee] results per page.
     * @return The list of [Followee] results.
     */
    @GET("/v1/users/{user_id}/following")
    fun listFollowingOfUser(@Path("user_id") userId: Long,
                            @Query("per_page") per_page: Int): Observable<Response<List<Followee>>>

}