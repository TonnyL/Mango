package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.AccessToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * Created by lizhaotailang on 2017/6/24.
 */
interface AccessTokenService {

    @POST
    @FormUrlEncoded
    fun getAccessToken(@Url url: String,
                       @Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("code") code: String,
                       @Field("redirect_uri") redirectUri: String): Observable<AccessToken>

}