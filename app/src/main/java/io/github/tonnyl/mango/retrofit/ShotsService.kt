package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by lizhaotailang on 2017/6/29.
 */

interface ShotsService {

    @GET("/v1/shots/{shot_id}")
    fun getShot(@Path("shot_id") shotId: Long): Observable<Response<Shot>>

    @GET("/v1/shots")
    fun listShots(@Query("list") list: String?,
                  @Query("timeframe") timeFrame: String?,
                  @Query("date") date: String?,
                  @Query("sort") sort: String?,
                  @Query("per_page") perPage: Int): Observable<Response<List<Shot>>>

    @GET("/v1/users/{user_id}/shots")
    fun getShotsOfUser(@Path("user_id") userId: Long,
                       @Query("per_page") perPage: String): Observable<Response<List<Shot>>>

    // Check if the authenticated user likes a shot
    @GET("/v1/shots/{shot_id}/like")
    fun checkLikeOfShot(@Path("shot_id") shotId: Long): Observable<Response<Like>>

    @GET("/v1/shots/{shot_id}/likes")
    fun listLikesForShot(@Path("shot_id") shotId: Long): Observable<Response<List<Like>>>

    @POST("/v1/shots/{shot_id}/like")
    fun likeShot(@Path("shot_id") shotId: Long): Observable<Response<Like>>

    @DELETE("/v1/shots/{shot_id}/like")
    fun unlikeShot(@Path("shot_id") shotId: Long): Observable<Response<Like>>

    @GET("/v1/shots/{shot_id}/buckets")
    fun listBucketsForShot(@Path("shot_id") shotId: Long): Observable<Response<List<Bucket>>>

    @GET("/v1/shots/{shot_id}/projects")
    fun listProjectsForShot(@Path("shot_id") shotId: Long): Observable<Response<List<Project>>>

    @GET("/v1/shots/{shot_id}/rebounds")
    fun listReboundsForShot(@Path("shot_id") shotId: Long): Observable<Response<List<Rebound>>>

    @GET("/v1/shots/{shot_id}/comments")
    fun listCommentsForShot(@Path("shot_id") shotId: Long,
                            @Query("per_page") perPage: Int): Observable<Response<List<Comment>>>

    @GET("/v1/shots/{shot_id}/comments/{comment_id}/likes")
    fun listLikesForComment(@Path("shot_id") shotId: Long,
                            @Path("comment_id") commentId: Long,
                            @Query("per_page") perPage: Int): Observable<Response<List<User>>>

    @POST("/v1/shots/{shot_id}/comments")
    fun createComment(@Path("shot_id") shotId: Long,
                      @Query("body") body: String): Observable<Response<Comment>>

    @PUT("/v1/shots/{shot_id}/comments/{arg1}")
    fun updateComment(@Path("shot_id") shotId: Long,
                      @Path("comment_id") commentId: Long): Observable<Response<Comment>>

    @DELETE("/v1/shots/{shot_id}/comments/{arg1}")
    fun deleteComment(@Path("shot_id") shotId: Long,
                      @Path("comment_id") commentId: Long): Observable<Response<Void>>

    @GET("/v1/shots/{shot_id}/comments/{arg1}/like")
    fun checkLikeForComment(@Path("shot_id") shotId: Long,
                            @Path("comment_id") commentId: Long): Observable<Response<Void>>

    @POST("/v1/shots/{shot_id}/comments/{arg1}/like")
    fun likeComment(@Path("shot_id") shotId: Long,
                    @Path("comment_id") commentId: Long): Observable<Response<Void>>

    @DELETE("/v1/shots/{shot_id}/comments/{arg1}/like")
    fun unlikeComment(@Path("shot_id") shotId: Long,
                      @Path("comment_id") commentId: Long): Observable<Response<Void>>
}