package io.github.tonnyl.mango.data.remote

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.datasource.AccessTokenDataSource
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.retrofit.AccessTokenService
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 */

object AccessTokenRemoteDataSource: AccessTokenDataSource {

    override fun init(context: Context) {

    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        return RetrofitClient.createService(AccessTokenService::class.java, null)
                .getAccessToken(ApiConstants.DRIBBBLE_GET_ACCESS_TOKEN_URL,
                        ApiConstants.CLIENT_ID,
                        ApiConstants.CLIENT_SECRET,
                        code!!,
                        ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI)
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        // Not required for the remote data source
        // because this function is only for [io.github.tonnyl.mango.data.local.AccessTokenLocalDataSource].
    }
}