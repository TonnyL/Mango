package io.github.tonnyl.mango.data.datasource

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 */

interface AccessTokenDataSource {

    fun init(context: Context)

    fun getAccessToken(id: Long?, code: String?): Observable<AccessToken>

    fun saveAccessToken(accessToken: AccessToken)

}