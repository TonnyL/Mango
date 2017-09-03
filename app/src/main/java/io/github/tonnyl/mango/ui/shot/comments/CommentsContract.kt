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
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/8.
 *
 * This specifies the contract between the view and the presenter.
 */
interface CommentsContract {

    interface View: BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showNetworkError()

        fun setEmptyViewVisibility(visible: Boolean)

        fun cancelSendingIndicator(clearText: Boolean)

        fun showCreateCommentFailed()

        fun showComments(comments: List<Comment>)

        fun notifyDataAllRemoved(size: Int)

        fun notifyDataAdded(startPosition: Int, size: Int)

        fun updateTitle(commentsCount: Int)

        fun setEditorVisible(visible: Boolean, avatarUrl: String)

    }

    interface Presenter: BasePresenter {

        fun createComment(body: String)

        fun loadComments()

        fun loadMoreComments()

    }

}