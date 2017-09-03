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
 *
 * [retrofit2.Retrofit] service all about one single [User]. Works with [RetrofitClient].
 */
interface UserService {

    /**
     * Get the authenticated user.
     *
     * @return The [User] detail.
     */
    @GET("/v1/user")
    fun getAuthenticatedUser(): Observable<User>

    /**
     * Check if you are following a specific user.
     *
     * @param userId The id of the user you want to check.
     * @return The following result.
     *         If you are following the user, the status code of response will be 204 (no content),
     *         or it will be 404 (not found).
     */
    @GET("/v1/user/following/{user_id}")
    fun checkFollowing(@Path("user_id") userId: Long): Observable<Response<Body>>

    /**
     * List shots for users followed by a user.
     * Listing shots from followed users requires the user to be authenticated with the `public` scope.
     * Also note that you can not retrieve more than 600 results, regardless of the number requested per page.
     *
     * @param per_page The amount of shot results per page.
     * @return The [Shot] results.
     */
    @GET("/v1/user/following/shots")
    fun listFollowingShots(@Query("per_page") per_page: Int): Observable<Response<List<Shot>>>

}