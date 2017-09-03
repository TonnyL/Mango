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

import io.github.tonnyl.mango.data.AccessToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * The [retrofit2.Retrofit] service all about [AccessToken].
 */
interface AccessTokenService {

    /**
     * Get the [AccessToken].
     *
     * @param clientId Required. The client ID you received from Dribbble when you [registered](https://dribbble.com/account/applications/new).
     * @param clientSecret Required. The client secret you received from Dribbble when you [registered](https://dribbble.com/account/applications/new).
     * @param code Required The code you received as a response to the pre-request.
     * @param redirectUri The URL in your application where users will be sent after authorization.
     *                    See details about [Redirect URLs](http://developer.dribbble.com/v1/oauth/#redirect-urls).
     * @return The [AccessToken] result.
     */
    @POST
    @FormUrlEncoded
    fun getAccessToken(@Url url: String,
                       @Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String,
                       @Field("redirect_uri") redirectUri: String): Observable<AccessToken>

}