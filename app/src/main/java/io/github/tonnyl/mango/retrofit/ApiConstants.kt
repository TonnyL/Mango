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

import io.github.tonnyl.mango.BuildConfig

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * There are 3 steps altogether for web application flow:
 * + 1. Redirect users to request Dribbble access.
 *   ```GET https://dribbble.com/oauth/authorize```
 *   The parameters are listed below:
 *   client_id    - [String] type. Required. The client ID you received from Dribbble when you [registered](https://dribbble.com/account/applications/new).
 *   redirect_uri - [String] type. The URL in your application where users will be sent after authorization.
 *                                 See details about [Redirect URLs](http://developer.dribbble.com/v1/oauth/#redirect-urls).
 *   scope        - [String] type. A space separated list of scopes.
 *                                 If not provided, scope defaults to the public scope for users that don’t have a valid token for the application.
 *                                 For users who do already have a valid token for the application,
 *                                 the user won’t be shown the authorization page with the list of scopes.
 *                                 Instead, this step of the flow will automatically complete with the same scopes that were user last time the user completed the flow.
 *   state        - [String] type. An unguessable random string.
 *                                 It is used to protect against cross-site request forgery attacks.
 *
 * + 2. Dribbble redirects back to your site.
 *   If the user accepts your request,
 *   Dribbble redirects back to your site with a temporary code in a code parameter as well as the state you provided in the previous step in a state parameter.
 *   If the states don’t match, the request has been created by a third party and the process should be aborted.
 *   Exchange this for an access token:
 *   ```POST https://dribbble.com/oauth/token```
 *   See [io.github.tonnyl.mango.retrofit.AccessTokenService] for more details.
 *
 * + 3. Use the access token to access the API.
 *   The access token allows you to make requests to the API on a behalf of a user.
 *   ```GET https://api.dribbble.com/v1/user?access_token=...```
 *
 */

class ApiConstants private constructor() {

    companion object {

        // May be reset and not commit to github if they are abused.
        val CLIENT_ID = BuildConfig.CLIENT_ID
        val CLIENT_SECRET = BuildConfig.CLIENT_SECRET
        val CLIENT_ACCESS_TOKEN = BuildConfig.CLIENT_ACCESS_TOKEN

        // Constant strings of Dribbble API
        val DRIBBBLE_V1_BASE_URL = "https://api.dribbble.com"
        val DRIBBBLE_AUTHORIZE_URL = "https://dribbble.com/oauth/authorize"
        val DRIBBBLE_GET_ACCESS_TOKEN_URL = "https://dribbble.com/oauth/token"

        // Callback urls
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI = "x-mango-oauth-dribbble://callback"
        //val DRIBBBLE_AUTHORIZE_CALLBACK_URI_SCHEMA: String = "x-mango-oauth-dribbble"
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI_HOST = "callback"
        val DRIBBBLE_AUTHORIZE_SCOPE = "public+write+comment+upload"

        // Amount of images per page
        val PER_PAGE = 20
    }

}