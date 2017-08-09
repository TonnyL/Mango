package io.github.tonnyl.mango.ui.user.followers

import io.github.tonnyl.mango.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/29.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.followers.FollowersFragment],
 * retrieves the data and update the ui as required.
 */
class FollowersPresenter(view: FollowersContract.View, userId: Long) : FollowersContract.Presenter {

    private val mView = view
    private val mUserId = userId
    private val mCompositeDisposable: CompositeDisposable

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        const val EXTRA_FOLLOWERS_TITLE = "EXTRA_FOLLOWERS_TITLE"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        val disposable = UserRepository.listFollowerOfUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        mView.showFollowers(it.toMutableList())
                        mView.setLoadingIndicator(false)
                    }
                }, {
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}