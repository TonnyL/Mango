package io.github.tonnyl.mango.ui.user

import io.github.tonnyl.mango.data.User
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class UserProfilePresenter(view: UserProfileContract.View, user: User) : UserProfileContract.Presenter {

    private var mView: UserProfileContract.View = view
    private var mCompositeDisposable: CompositeDisposable

    private val mUser = user

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.showUserInfo(mUser)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun checkFollowing() {

    }

    override fun toggleFollow() {

    }

    override fun getUserId(): Long {
        return mUser.id
    }

}