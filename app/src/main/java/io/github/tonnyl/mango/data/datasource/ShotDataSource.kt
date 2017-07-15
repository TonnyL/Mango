package io.github.tonnyl.mango.data.datasource

import io.github.tonnyl.mango.data.*
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/7/3.
 */

interface ShotDataSource {

    fun getShot(shotId: Long): Observable<Response<Shot>>

    fun checkLike(shotId: Long): Observable<Response<Like>>

    fun likeShot(shotId: Long): Observable<Response<Like>>

    fun unlikeShot(shotId: Long): Observable<Response<Like>>

    fun listComments(shotId: Long, page: Int): Observable<Response<List<Comment>>>

    fun createComment(shotId: Long, body: String): Observable<Response<Comment>>

    fun listLikes(shotId: Long, page: Int): Observable<Response<List<Like>>>

    fun listBuckets(shotId: Long): Observable<Response<List<Bucket>>>

    fun listAttachments(shotId: Long): Observable<Response<List<Attachment>>>

}