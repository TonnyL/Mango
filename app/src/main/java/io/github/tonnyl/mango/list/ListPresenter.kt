package io.github.tonnyl.mango.list

import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.repository.ShotRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/3.
 */

class ListPresenter(view: ListContract.View) : ListContract.Presenter {

    private val mView = view
    private var mCompositeDisposable: CompositeDisposable? = null

    private var mId: Long? = null
    private var mType: Int? = null

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.setUpViews(R.string.comments, true)
        if (mType == ListActivity.TYPE_COMMENTS) {
            fetchComments()
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable?.clear()
    }

    override fun setId(id: Long) {
        mId = id
    }

    override fun setType(type: Int) {
        mType = type
    }

    override fun createComment(body: String) {
        ShotRepository.createComment(mId!!, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.body() != null) {
                        mView.cancelSendingIndicator(true)
                    } else {
                        mView.cancelSendingIndicator(false)
                        mView.showMessage(R.string.add_comment_failed)
                    }
                }, { error ->
                    mView.showMessage(error.message)
                    mView.cancelSendingIndicator(false)
                })
    }

    override fun likeComment(commentId: Long) {

    }

    override fun deleteComment(commentId: Long) {

    }

    private fun fetchComments() {
        mView.setLoadingIndicator(true)
        if (mType == ListActivity.TYPE_COMMENTS) {
            ShotRepository.listComments(mId!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        if (response.body() != null) {
                            mView.setLoadingIndicator(false)
                            mView.showComments(response.body()!!.toMutableList())
                        }
                    }, { error ->
                        mView.setLoadingIndicator(false)
                        mView.showMessage(error.message)
                    })
        } else if (mType == ListActivity.TYPE_LIKES) {
            mView.setUpViews(R.string.likes, false)
        } else if (mType == ListActivity.TYPE_BUCKETS) {
            mView.setUpViews(R.string.buckets, false)
        } else {
            mView.setUpViews(R.string.attachments, false)
        }
    }

}