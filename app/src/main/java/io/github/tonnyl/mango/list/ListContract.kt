package io.github.tonnyl.mango.list

import android.support.annotation.StringRes
import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView
import io.github.tonnyl.mango.data.Attachment
import io.github.tonnyl.mango.data.Bucket
import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like

/**
 * Created by lizhaotailang on 2017/7/3.
 */
interface ListContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showMessage(message: String?)

        fun showMessage(@StringRes resId: Int)

        fun setUpViews(@StringRes titleId: Int, showAddComment: Boolean)

        fun cancelSendingIndicator(clearText: Boolean)

        fun showComments(comments: MutableList<Comment>)

        fun showLikes(likes: MutableList<Like>)

        fun showBuckets(buckets: MutableList<Bucket>)

        fun showAttachments(attachments: MutableList<Attachment>)

    }

    interface Presenter : BasePresenter {

        fun setId(id: Long)

        fun setType(type: Int)

        fun createComment(body: String)

        fun likeComment(commentId: Long)

        fun deleteComment(commentId: Long)

    }

}