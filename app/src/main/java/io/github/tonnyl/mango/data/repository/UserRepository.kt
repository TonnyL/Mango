package io.github.tonnyl.mango.data.repository

import android.content.Context
import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.UserDataSource
import io.github.tonnyl.mango.data.local.UserLocalDataSource
import io.github.tonnyl.mango.data.remote.UserRemoteDataSource
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.UserService
import io.github.tonnyl.mango.retrofit.UsersService
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body


/**
 * Created by lizhaotailang on 2017/6/27.
 */

object UserRepository : UserDataSource {

    private val mUserService = RetrofitClient.createService(UserService::class.java, AccountManager.accessToken)
    private val mUsersService = RetrofitClient.createService(UsersService::class.java, AccountManager.accessToken)

    override fun init(context: Context) {
        UserLocalDataSource.init(context)
    }

    override fun getAuthenticatedUser(id: Long?): Observable<User> {
        if (id == null) {
            return UserRemoteDataSource.getAuthenticatedUser(null)
        }

        return UserLocalDataSource.getAuthenticatedUser(id)
    }

    override fun getAllAuthenticatedUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAuthenticatedUser(user: User) {
        UserLocalDataSource.saveAuthenticatedUser(user)
    }

    override fun updateAuthenticatedUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAuthenticatedUser(user: User): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkFollowing(userId: Long): Observable<Response<Body>> {
        return mUserService.checkFollowing(userId)
    }

    override fun follow(userId: Long): Observable<Response<Body>> {
        return mUsersService.follow(userId)
    }

    override fun unfollow(userId: Long): Observable<Response<Body>> {
        return mUsersService.unfollow(userId)
    }

    override fun listFollowerOfUser(userId: Long): Observable<Response<List<Follower>>> {
        return mUserService.listFollowerOfUser(userId, ApiConstants.PER_PAGE)
    }

    override fun listFolloweeOfUser(userId: Long): Observable<Response<List<Followee>>> {
        return mUserService.listFollowingOfUser(userId, ApiConstants.PER_PAGE)
    }

}