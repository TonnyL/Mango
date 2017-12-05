/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.data.repository

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.ShotsService
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body

/**
 * Created by lizhaotailang on 2017/7/3.
 */

object ShotRepository {

    private val mShotsService = RetrofitClient.createService(ShotsService::class.java, AccessTokenManager.accessToken)

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

    fun listComments(shotId: Long): Observable<Response<List<Comment>>> {
        return mShotsService.listCommentsForShot(shotId)
    }

    fun listCommentsOfNextPage(url: String): Observable<Response<List<Comment>>> {
        return mShotsService.listCommentsOfNextPage(url)
    }

    fun createComment(shotId: Long, body: String): Observable<Response<Comment>> {
        return mShotsService.createComment(shotId, body)
    }

    fun listLikes(shotId: Long): Observable<Response<List<Like>>> {
        return mShotsService.listLikesForShot(shotId)
    }

    fun listLikesOfNextPage(url: String): Observable<Response<List<Like>>> {
        return mShotsService.listLikesOfNextPage(url)
    }
}