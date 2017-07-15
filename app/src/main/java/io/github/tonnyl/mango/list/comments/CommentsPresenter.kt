package io.github.tonnyl.mango.list.comments

import io.github.tonnyl.mango.data.repository.ShotRepository
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/8.
 */
class CommentsPresenter(view: CommentsContract.View, id: Long) : CommentsContract.Presenter {

    private val mView = view
    private val mId = id
    private var mCompositeDisposable: CompositeDisposable

    private var mPageCount = 0
    private var mIsFirstLoad = true
    private var mHasMore = true

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        fetchComments()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun createComment(body: String) {
        val disposable = ShotRepository
                .createComment(mId, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        mView.cancelSendingIndicator(true)
                    } ?: run {
                        mView.cancelSendingIndicator(false)
                        mView.showMessage(null)
                    }
                }, { error ->
                    mView.showMessage(error.message)
                    mView.cancelSendingIndicator(false)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun fetchComments() {
        if (mIsFirstLoad) {
            mView.setLoadingIndicator(true)
            mIsFirstLoad = false
        }

        if (!mHasMore) {
            return
        }

        val disposable = ShotRepository
                .listComments(mId, mPageCount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.setLoadingIndicator(false)
                    response.body()?.let {
                        mView.showComments(it.toMutableList())
                        mHasMore = (it.size % ApiConstants.PER_PAGE == 0)
                        if (!mHasMore) {
                            mPageCount = 0
                        } else {
                            mPageCount = (it.size / ApiConstants.PER_PAGE) + 1
                        }
                    }
                }, { error ->
                    mView.setLoadingIndicator(false)
                    mView.showMessage(error.message)
                })
        mCompositeDisposable.add(disposable)
    }

}