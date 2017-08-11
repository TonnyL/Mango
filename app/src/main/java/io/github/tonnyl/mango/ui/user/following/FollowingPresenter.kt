package io.github.tonnyl.mango.ui.user.following

import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.PageLinks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/29.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.user.following.FollowingFragment],
 * retrieves the data and update the ui as required.
 */
class FollowingPresenter(view: FollowingContract.View, userId: Long) : FollowingContract.Presenter {

    private val mView = view
    private val mUserId = userId
    private val mCompositeDisposable: CompositeDisposable

    private var mNextPageUrl: String? = null
    private var mCachedFollowees = arrayListOf<Followee>()

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        const val EXTRA_FOLLOWING_TITLE = "EXTRA_FOLLOWING_TITLE"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadFollowing()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadFollowing() {
        mView.setLoadingIndicator(true)
        val disposable = UserRepository.listFolloweeOfUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.setLoadingIndicator(false)
                    mNextPageUrl = PageLinks(response).next

                    response.body()?.let {
                        if (it.isNotEmpty()) {
                            if (mCachedFollowees.isNotEmpty()) {
                                val size = mCachedFollowees.size
                                mCachedFollowees.clear()
                                mView.notifyDataAllRemoved(size)
                                mCachedFollowees.addAll(it)
                                mView.notifyDataAdded(0, mCachedFollowees.size)
                            } else {
                                mCachedFollowees.addAll(it)
                                mView.showFollowings(mCachedFollowees)
                            }
                        } else {
                            mView.setEmptyViewVisibility(it.isEmpty())
                        }
                    }
                }, {
                    mView.setEmptyViewVisibility(true)
                    mView.setLoadingIndicator(false)
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadMoreFollowing() {
        mNextPageUrl?.let {
            val disposable = UserRepository.listFolloweesOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mCachedFollowees.size
                            mCachedFollowees.addAll(it)
                            mView.notifyDataAdded(size, it.size)
                        }
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }

    }

}