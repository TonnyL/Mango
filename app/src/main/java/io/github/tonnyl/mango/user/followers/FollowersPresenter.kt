package io.github.tonnyl.mango.user.followers

import io.github.tonnyl.mango.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/29.
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
                    }
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}