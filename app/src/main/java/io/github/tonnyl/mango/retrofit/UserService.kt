package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.User
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by lizhaotailang on 2017/6/24.
 */

interface UserService {

    @GET("/v1/user")
    fun getAuthenticatedUser(): Observable<User>

}