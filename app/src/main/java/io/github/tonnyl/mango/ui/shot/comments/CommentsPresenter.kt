/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.ui.shot.comments

import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.data.repository.ShotRepository
import io.github.tonnyl.mango.util.AccessTokenManager
import io.github.tonnyl.mango.util.PageLinks
import io.github.tonnyl.mango.util.UserUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by lizhaotailang on 2017/7/8.
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.shot.comments.CommentsFragment],
 * retrieves the data and update the ui as required.
 */
class CommentsPresenter(view: CommentsContract.View, shot: Shot) : CommentsContract.Presenter {

    private val mView = view
    private val mShot = shot
    private var mCompositeDisposable: CompositeDisposable

    private val mCachedComments = arrayListOf<Comment>()
    private var mNextPageUrl: String? = null

    companion object {
        @JvmField
        val EXTRA_SHOT = "EXTRA_SHOT"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadComments()

        mView.updateTitle(mShot.commentsCount)

        AccessTokenManager.accessToken?.let {
            AuthUserRepository.getAuthenticatedUser(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mView.setEditorVisible(UserUtils.canUserComment(it), it.avatarUrl)
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun createComment(body: String) {
        val disposable = ShotRepository
                .createComment(mShot.id, body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        mView.cancelSendingIndicator(true)

                        if (mNextPageUrl == null) {
                            insertNewComment(body)
                        }
                    } ?: run {
                        mView.cancelSendingIndicator(false)
                    }
                }, {
                    mView.cancelSendingIndicator(false)
                    mView.showCreateCommentFailed()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadComments() {
        mView.setLoadingIndicator(true)

        val disposable = ShotRepository
                .listComments(mShot.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mNextPageUrl = PageLinks(response).next
                    mView.setLoadingIndicator(false)

                    response.body()?.let {
                        mView.setEmptyViewVisibility(it.isEmpty())
                        if (it.isNotEmpty()) {
                            if (mCachedComments.isNotEmpty()) {
                                val size = mCachedComments.size
                                mCachedComments.clear()
                                mView.notifyDataAllRemoved(size)
                                mCachedComments.addAll(it)
                                mView.notifyDataAdded(0, mCachedComments.size)
                            } else {
                                mCachedComments.addAll(it)
                                mView.showComments(mCachedComments)
                            }
                        }
                    }
                }, {
                    mView.setEmptyViewVisibility(true)
                    mView.setLoadingIndicator(false)
                    it.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun loadMoreComments() {
        mNextPageUrl?.let {
            val disposable = ShotRepository
                    .listCommentsOfNextPage(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ response ->
                        mNextPageUrl = PageLinks(response).next

                        response.body()?.let {
                            val size = mCachedComments.size
                            mCachedComments.addAll(it)
                            mView.notifyDataAdded(size, it.size)
                        }
                    }, {
                        mView.showNetworkError()
                        it.printStackTrace()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    private fun insertNewComment(commentBody: String) {
        AccessTokenManager.accessToken?.let { token ->
            AuthUserRepository.getAuthenticatedUser(token.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mCachedComments.add(Comment(id = token.id, body = commentBody, likesCount = 0, likesUrl = "", createdAt = Date(System.currentTimeMillis()), updatedAt = Date(System.currentTimeMillis()), user = it))
                        mView.notifyDataAdded(mCachedComments.size - 1, 1)
                    }, {
                        it.printStackTrace()
                    })
        }
    }

}