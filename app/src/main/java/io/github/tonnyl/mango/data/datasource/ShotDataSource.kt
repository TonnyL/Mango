package io.github.tonnyl.mango.data.datasource

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.data.Shot
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/7/3.
 */

interface ShotDataSource {

    fun getShot(shotId: Long): Observable<Response<Shot>>

    fun checkLikeOfShot(shotId: Long): Observable<Response<Like>>

    fun likeShot(shotId: Long): Observable<Response<Like>>

    fun unlikeShot(shotId: Long): Observable<Response<Like>>

    fun listComments(shotId: Long): Observable<Response<List<Comment>>>

    fun createComment(shotId: Long, body: String): Observable<Response<Comment>>

}