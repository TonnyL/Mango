package io.github.tonnyl.mango.data.repository

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.local.AccessTokenLocalDataSource
import io.github.tonnyl.mango.data.remote.AccessTokenRemoteDataSource
import io.github.tonnyl.mango.data.datasource.AccessTokenDataSource
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.Observable

/**
 * Created by lizhaotailang on 2017/6/27.
 */

object AccessTokenRepository : AccessTokenDataSource {

    override fun init(context: Context) {
        AccessTokenLocalDataSource.init(context)
    }

    override fun getAccessToken(id: Long?, code: String?): Observable<AccessToken> {
        if (code != null) {
            return AccessTokenRemoteDataSource.getAccessToken(id, code)
                    .flatMap { accessToken ->
                        Observable.just(accessToken)
                                .doOnNext {
                                    AccountManager.accessToken = accessToken
                                }
                    }
        }

        return AccessTokenLocalDataSource.getAccessToken(id, code)
                .flatMap { accessToken ->
                    Observable.just(accessToken)
                            .doOnNext {
                                AccountManager.accessToken = accessToken
                            }
                }
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        AccessTokenLocalDataSource.saveAccessToken(accessToken)
    }
}