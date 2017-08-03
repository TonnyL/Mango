package io.github.tonnyl.mango.data.remote

import android.content.Context
import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.datasource.UserDataSource
import io.github.tonnyl.mango.retrofit.RetrofitClient
import io.github.tonnyl.mango.retrofit.UserService
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@Singleton
object UserRemoteDataSource : UserDataSource {

    private val mUserService: UserService = RetrofitClient.createService(UserService::class.java, AccountManager.accessToken)

    override fun init(context: Context) {
        // Not required for the remote data source
        // because this function is only for [io.github.tonnyl.mango.data.local.UserLocalDataSource].
    }

    override fun getAuthenticatedUser(id: Long?): Observable<User> {
        return mUserService.getAuthenticatedUser()
    }

    override fun getAllAuthenticatedUsers(): Observable<List<User>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveAuthenticatedUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateAuthenticatedUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAuthenticatedUser(user: User): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkFollowing(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun follow(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unfollow(userId: Long): Observable<Response<Body>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listFollowerOfUser(userId: Long): Observable<Response<List<Follower>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun listFolloweeOfUser(userId: Long): Observable<Response<List<Followee>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}