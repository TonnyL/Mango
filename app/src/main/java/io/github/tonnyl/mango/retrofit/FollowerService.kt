package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by lizhaotailang on 2017/7/1.
 */

interface FollowerService {

    @GET("/v1/user/following/shots")
    fun listFollowingShots(@Query("per_page")per_page: Int): Observable<Response<List<Shot>>>

}