package io.github.tonnyl.mango.ui.user

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.UserProfileFragment],
 * retrieves the data and update the ui as required.
 */

class UserProfilePresenter(view: UserProfileContract.View, user: User) : UserProfileContract.Presenter {

    private var mView: UserProfileContract.View = view
    private var mCompositeDisposable: CompositeDisposable
    private var mFollowingChecked = false
    private var mIsFollowing = false

    private var mUser = user

    companion object {
        @JvmField
        val EXTRA_USER = "EXTRA_USER"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.showUserInfo(mUser)
        if (AccountManager.accessToken?.id == mUser.id) {
            mView.setFollowable(false)
            getUpdatedUserInfo()
        } else {
            checkFollowing()
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun checkFollowing() {
        val disposable = UserRepository.checkFollowing(mUser.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.code() == 204) {
                        mIsFollowing = true
                        mView.setFollowing(true)
                    } else if (it.code() == 404) {
                        mIsFollowing = false
                        mView.setFollowing(false)
                    }
                    mFollowingChecked = true
                    mView.setFollowable(true)
                }, {
                    mFollowingChecked = false
                    mView.setFollowable(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun toggleFollow() {
        if (mIsFollowing) {
            UserRepository.unfollow(mUser.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mIsFollowing = false
                        mView.setFollowing(false)
                    }, {

                    })
        } else {
            UserRepository.follow(mUser.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mIsFollowing = true
                        mView.setFollowing(true)
                    }, {

                    })
        }
    }

    override fun getUser(): User {
        return mUser
    }

    private fun getUpdatedUserInfo() {
        val disposable = AuthUserRepository.refreshAuthenticatedUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mUser = it
                    mView.showUserInfo(it)
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}