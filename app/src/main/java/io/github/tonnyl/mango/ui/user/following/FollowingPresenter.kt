package io.github.tonnyl.mango.ui.user.following

import io.github.tonnyl.mango.data.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/29.
 */
class FollowingPresenter(view: FollowingContract.View, userId: Long) : FollowingContract.Presenter {

    private val mView = view
    private val mUserId = userId
    private val mCompositeDisposable: CompositeDisposable

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        const val EXTRA_FOLLOWING_TITLE = "EXTRA_FOLLOWING_TITLE"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        val disposable = UserRepository.listFolloweeOfUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        mView.showFollowings(it.toMutableList())
                    }
                    mView.setLoadingIndicator(false)
                }, {
                    mView.setLoadingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}