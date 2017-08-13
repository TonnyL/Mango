package io.github.tonnyl.mango.ui.user.followers

import io.github.tonnyl.mango.data.Follower
import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.PageLinks
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

    private val mCachedFollowers = arrayListOf<Follower>()
    private var mNextPageUrl: String? = null

    companion object {
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
        const val EXTRA_FOLLOWERS_TITLE = "EXTRA_FOLLOWERS_TITLE"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadFollowers()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun loadFollowers() {
        mView.setLoadingIndicator(true)
        val disposable = UserRepository.listFollowersOfUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.setLoadingIndicator(false)
                    mNextPageUrl = PageLinks(response).next

                    response.body()?.let {
                        mView.setEmptyViewVisibility(it.isEmpty())
                        if (it.isNotEmpty()) {
                            if (mCachedFollowers.isNotEmpty()) {
                                val size = mCachedFollowers.size
                                mCachedFollowers.clear()
                                mView.notifyDataAllRemoved(size)
                                mCachedFollowers.addAll(it)
                                mView.notifyDataAdded(0, mCachedFollowers.size)
                            } else {
                                mCachedFollowers.addAll(it)
                                mView.showFollowers(mCachedFollowers)
                            }
                        }
                    }
                }, {
                    mView.setLoadingIndicator(false)
                    mView.setEmptyViewVisibility(true)
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadMoreFollowers() {
        mNextPageUrl?.let {
            val disposable = UserRepository.listFollowersOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mCachedFollowers.size
                            mCachedFollowers.addAll(it)
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