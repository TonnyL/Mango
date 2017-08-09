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