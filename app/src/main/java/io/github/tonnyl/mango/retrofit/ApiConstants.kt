package io.github.tonnyl.mango.retrofit

/**
 * Created by lizhaotailang on 2017/6/24.
 */

class ApiConstants private constructor() {

    companion object {

        val CLIENT_ID: String = "880e6d00af514d4c2046b3ea49c82397ddc0e81f3239dabd5a79a91dc9003aeb"
        val CLIENT_SECRET: String = "da035b73967362330aea7fc9cc9ce66a6e945b87c34e3a7cbf1a871e4bf13c61"
        val CLIENT_ACCESS_TOKEN = "2345496566b85d63b207200b6fa64a9de8dbe4654e9883ab67b477e47aa5230a"

        // Constant strings of Dribbble API
        val DRIBBBLE_V1_BASE_URL: String = "https://api.dribbble.com"
        val DRIBBBLE_AUTHORIZE_URL: String = "https://dribbble.com/oauth/authorize"
        val DRIBBBLE_GET_ACCESS_TOKEN_URL: String = "https://dribbble.com/oauth/token"


        // Callback urls
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI: String = "x-mango-oauth-dribbble://callback"
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI_SCHEMA: String = "x-mango-oauth-dribbble"
        val DRIBBBLE_AUTHORIZE_CALLBACK_URI_HOST: String = "callback"
        val DRIBBBLE_AUTHORIZE_SCOPE: String = "public+write+comment+upload"

        // Amount of images per page
        val PER_PAGE: Int = 20
    }

}