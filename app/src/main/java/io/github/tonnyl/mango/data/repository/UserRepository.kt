package io.github.tonnyl.mango.data.repository

import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.UserService
import io.github.tonnyl.mango.retrofit.UsersService
import io.github.tonnyl.mango.util.AccessTokenManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body

/**
 * Created by lizhaotailang on 2017/8/9.
 */
object UserRepository {

    private val mUsersService = RetrofitClient.createService(UsersService::class.java, AccessTokenManager.accessToken)
    private val mUserService = RetrofitClient.createService(UserService::class.java, AccessTokenManager.accessToken)

    fun checkFollowing(userId: Long): Observable<Response<Body>> {
        return mUserService.checkFollowing(userId)
    }

    fun follow(userId: Long): Observable<Response<Body>> {
        return mUsersService.follow(userId)
    }

    fun unfollow(userId: Long): Observable<Response<Body>> {
        return mUsersService.unfollow(userId)
    }

    fun listFollowersOfUser(userId: Long): Observable<Response<List<Follower>>> {
        return mUsersService.listFollowersOfUser(userId, ApiConstants.PER_PAGE)
    }

    fun listFollowersOfNextPage(url: String): Observable<Response<List<Follower>>> {
        return mUsersService.listFollowersOfNextPage(url)
    }

    fun listFolloweeOfUser(userId: Long): Observable<Response<List<Followee>>> {
        return mUsersService.listFollowingOfUser(userId, ApiConstants.PER_PAGE)
    }

    fun listFolloweesOfNextPage(url: String): Observable<Response<List<Followee>>> {
        return mUsersService.listFollowingOfNextPage(url)
    }

}