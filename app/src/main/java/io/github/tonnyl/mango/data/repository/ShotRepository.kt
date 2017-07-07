package io.github.tonnyl.mango.data.repository

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.datasource.ShotDataSource
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.ShotsService
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/7/3.
 */

object ShotRepository : ShotDataSource {

    private val mShotsService = RetrofitClient.createService(ShotsService::class.java, AccountManager.accessToken)

    override fun getShot(shotId: Long): Observable<Response<Shot>> {
        return mShotsService.getShot(shotId)
    }

    override fun checkLikeOfShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.checkLikeOfShot(shotId)
    }

    override fun likeShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.likeShot(shotId)
    }

    override fun unlikeShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.unlikeShot(shotId)
    }

    override fun listComments(shotId: Long): Observable<Response<List<Comment>>> {
        return mShotsService.listCommentsForShot(shotId, ApiConstants.PER_PAGE)
    }

    override fun createComment(shotId: Long, body: String): Observable<Response<Comment>> {
        return mShotsService.createComment(shotId, body)
    }

}