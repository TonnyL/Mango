package io.github.tonnyl.mango.data.datasource

import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/6/30.
 */

interface ShotsDataSource {

    fun listFollowingShots(): Observable<Response<List<Shot>>>

    fun listShots(type: Int, page: Int): Observable<Response<List<Shot>>>

}