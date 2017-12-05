/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by lizhaotailang on 2017/6/29.
 *
 * [retrofit2.Retrofit] service all about [Shot]s. Works with [RetrofitClient].
 */

interface ShotsService {

    /**
     * Get a specific shot.
     *
     * @param shotId The shot id.
     * @return The [Shot] info.
     */
    @GET("/v1/shots/{shot_id}")
    fun getShot(@Path("shot_id") shotId: Long): Observable<Response<Shot>>

    /**
     * Get a list of shots.
     *
     * @param list The type of list. Limit the results to a specific type with the following possible values:
     *             animated, attachments, debuts, playoffs, rebounds, teams.
     *             Default value is results of any type.
     * @param timeFrame A period of time to limit the results to with the following possible values:
     *             week, month, year, ever. Note that the value is ignored when sorting with recent.
     *             Default value is results from now.
     * @param date Limit the timeframe to a specific date, week, month, or year.
     *             Must be in the format of YYYY-MM-DD.
     * @param sort The sort field with the following possible values: comments, recent, views.
     *             Default value is results that are sorted by popularity.
     * @param perPage The amount of shot results per page.
     * @return The [Shot] results.
     */
    @GET("/v1/shots")
    fun listShots(@Query("list") list: String?,
                  @Query("timeframe") timeFrame: String?,
                  @Query("date") date: String?,
                  @Query("sort") sort: String?,
                  @Query("per_page") perPage: Int = ApiConstants.PER_PAGE): Observable<Response<List<Shot>>>

    /**
     * Get a shot list of next page according to the pre-request.
     *
     * @param url The next page url.
     * @return The shot list.
     */
    @GET
    fun listShotsOfNextPage(@Url url: String): Observable<Response<List<Shot>>>

    /**
     * Check if the user likes a shot. Authentication required.
     * @param shotId The shot id.
     * @return Whether the user likes the shot.
     *         If likes, the status code of response will be 200,
     *         or it will be 404 (not found).
     */
    @GET("/v1/shots/{shot_id}/like")
    fun checkLikeOfShot(@Path("shot_id") shotId: Long): Observable<Response<Like>>

    /**
     * List likes for a specific shot.
     *
     * @param shotId The shot id of shot.
     * @param perPage The amount of shot results per page.
     * @return The [Like] results.
     */
    @GET("/v1/shots/{shot_id}/likes")
    fun listLikesForShot(@Path("shot_id") shotId: Long,
                         @Query("per_page") perPage: Int = ApiConstants.PER_PAGE): Observable<Response<List<Like>>>

    /**
     * Get a like list of next page according to the pre-request.
     *
     * @param url The next page url.
     * @return The like list.
     */
    @GET
    fun listLikesOfNextPage(@Url url: String): Observable<Response<List<Like>>>

    /**
     * Like a shot. Authentication with `write` scope required.
     *
     * @param shotId The shot id.
     * @return The [Like] detail. If succeed, the status code of response will be 201 (created).
     */
    @POST("/v1/shots/{shot_id}/like")
    fun likeShot(@Path("shot_id") shotId: Long): Observable<Response<Like>>

    /**
     * Unlike a shot. Same as [likeShot], authentication with 'write' scope required.
     *
     * @param shotId The shot id.
     * @return The response of request. If succeed, the status code of response will be 204 (no content).
     */
    @DELETE("/v1/shots/{shot_id}/like")
    fun unlikeShot(@Path("shot_id") shotId: Long): Observable<Response<Body>>

    /**
     * List comments for a specific shot.
     *
     * @param shotId The shot id.
     * @param perPage The amount of shot results per page.
     * @return The [Comment] results.
     */
    @GET("/v1/shots/{shot_id}/comments")
    fun listCommentsForShot(@Path("shot_id") shotId: Long,
                            @Query("per_page") perPage: Int = ApiConstants.PER_PAGE): Observable<Response<List<Comment>>>

    /**
     * Get a comment list of next page according to the pre-request.
     *
     * @param url The next page url.
     * @return The comment list.
     */
    @GET
    fun listCommentsOfNextPage(@Url url: String): Observable<Response<List<Comment>>>

    /**
     * Create a comment for a specific shot.
     * Creating a comment requires the user to be authenticated with the `comment` scope.
     * The authenticated user must also be a player or team.
     * Any username mentions, such as '@simplebits', are automatically parsed and linked.
     *
     * @param shotId The shot id.
     * @param body Required. The contents of [Comment].
     * <p>
     *     Example:
     *     {"body" : "<p>Could he somehow make the shape of an \"S\" with his arms?
     *     I feel like i see potential for some hidden shapes in here...</p>\n\n<p>Looks fun!\n</p>"}
     *
     * @return The comment detail. If succeed, the status code of response will be 201 (created).
     */
    @POST("/v1/shots/{shot_id}/comments")
    fun createComment(@Path("shot_id") shotId: Long,
                      @Query("body") body: String): Observable<Response<Comment>>

}