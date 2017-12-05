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

import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.retrofit.*
import io.github.tonnyl.mango.ui.main.shots.ShotsPagePresenter
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable
import retrofit2.Response

/**
 * Created by lizhaotailang on 2017/6/30.
 */

object ShotsRepository {

    private val mShotsService: ShotsService = RetrofitClient.createService(ShotsService::class.java, AccessTokenManager.accessToken)
    private val mUserService: UserService = RetrofitClient.createService(UserService::class.java, AccessTokenManager.accessToken)
    private val mUsersService: UsersService = RetrofitClient.createService(UsersService::class.java, AccessTokenManager.accessToken)

    fun listFollowingShots(): Observable<Response<List<Shot>>> {
        return mUserService.listFollowingShots(ApiConstants.PER_PAGE)
    }

    fun listShots(type: Int): Observable<Response<List<Shot>>> {
        val timeframe: String? = null
        val date: String? = null

        val sort: String? = if (type == ShotsPagePresenter.TYPE_RECENT) {
            "recent"
        } else {
            null
        }

        val queryList: String? = if (type == ShotsPagePresenter.TYPE_DEBUTS) {
            "debuts"
        } else {
            null
        }

        return mShotsService.listShots(queryList, timeframe, date, sort)
    }

    fun listShotsOfNextPage(url: String): Observable<Response<List<Shot>>> {
        return mShotsService.listShotsOfNextPage(url)
    }

    fun listShotsOfUser(userId: Long): Observable<Response<List<Shot>>> {
        return mUsersService.listShotsOfUser(userId)
    }

    fun listShotsOfUserOfNextPage(url: String): Observable<Response<List<Shot>>> {
        return mUsersService.listShotsOfNextPage(url)
    }

    fun listLikedShotsOfUser(userId: Long): Observable<Response<List<LikedShot>>> {
        return mUsersService.listLikedShotsOfUser(userId)
    }

    fun listLikedShotOfNextPage(url: String): Observable<Response<List<LikedShot>>> {
        return mUsersService.listLikedShotsOfNextPage(url)
    }

}
