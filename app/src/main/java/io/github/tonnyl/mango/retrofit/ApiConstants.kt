package io.github.tonnyl.mango.retrofit

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
        val CLIENT_ID: String = "880e6d00af514d4c2046b3ea49c82397ddc0e81f3239dabd5a79a91dc9003aeb"
        val CLIENT_SECRET: String = "da035b73967362330aea7fc9cc9ce66a6e945b87c34e3a7cbf1a871e4bf13c61"
        val CLIENT_ACCESS_TOKEN = "2345496566b85d63b207200b6fa64a9de8dbe4654e9883ab67b477e47aa5230a"

        // Constant strings of Dribbble API
        val DRIBBBLE_V1_BASE_URL: String = "https://api.dribbble.com"
        val DRIBBBLE_AUTHORIZE_URL: String = "https://dribbble.com/oauth/authorize"
        val DRIBBBLE_GET_ACCESS_TOKEN_URL: String = "https://dribbble.com/oauth/token"

        // Callback urls
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI: String = "x-mango-oauth-dribbble://callback"
        //val DRIBBBLE_AUTHORIZE_CALLBACK_URI_SCHEMA: String = "x-mango-oauth-dribbble"
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI_HOST: String = "callback"
        val DRIBBBLE_AUTHORIZE_SCOPE: String = "public+write+comment+upload"

        // Amount of images per page
        val PER_PAGE: Int = 20
    }

}