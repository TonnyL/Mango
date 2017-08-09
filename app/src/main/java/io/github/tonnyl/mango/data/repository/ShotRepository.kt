package io.github.tonnyl.mango.data.repository

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.ShotsService
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body

/**
 * Created by lizhaotailang on 2017/7/3.
 */

object ShotRepository {

    private val mShotsService = RetrofitClient.createService(ShotsService::class.java, AccountManager.accessToken)

    fun getShot(shotId: Long): Observable<Response<Shot>> {
        return mShotsService.getShot(shotId)
    }

    fun checkLike(shotId: Long): Observable<Response<Like>> {
        return mShotsService.checkLikeOfShot(shotId)
    }

    fun likeShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.likeShot(shotId)
    }

    fun unlikeShot(shotId: Long): Observable<Response<Body>> {
        return mShotsService.unlikeShot(shotId)
    }

    fun listComments(shotId: Long, page: Int): Observable<Response<List<Comment>>> {
        return mShotsService.listCommentsForShot(shotId, page = page)
    }

    fun createComment(shotId: Long, body: String): Observable<Response<Comment>> {
        return mShotsService.createComment(shotId, body)
    }

    fun listLikes(shotId: Long, page: Int): Observable<Response<List<Like>>> {
        return mShotsService.listLikesForShot(shotId, page = page)
    }

}