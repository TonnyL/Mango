package io.github.tonnyl.mango.data.repository

import io.github.tonnyl.mango.data.*
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

    override fun checkLike(shotId: Long): Observable<Response<Like>> {
        return mShotsService.checkLikeOfShot(shotId)
    }

    override fun likeShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.likeShot(shotId)
    }

    override fun unlikeShot(shotId: Long): Observable<Response<Like>> {
        return mShotsService.unlikeShot(shotId)
    }

    override fun listComments(shotId: Long, page: Int): Observable<Response<List<Comment>>> {
        return mShotsService.listCommentsForShot(shotId, page = page)
    }

    override fun createComment(shotId: Long, body: String): Observable<Response<Comment>> {
        return mShotsService.createComment(shotId, body)
    }

    override fun listLikes(shotId: Long, page: Int): Observable<Response<List<Like>>> {
        return mShotsService.listLikesForShot(shotId, page = page)
    }

    override fun listBuckets(shotId: Long): Observable<Response<List<Bucket>>> {
        return mShotsService.listBucketsForShot(shotId)
    }

    override fun listAttachments(shotId: Long): Observable<Response<List<Attachment>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}