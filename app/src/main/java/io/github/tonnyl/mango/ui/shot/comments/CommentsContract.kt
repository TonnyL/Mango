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

        fun showMessage(message: String?)

        fun cancelSendingIndicator(clearText: Boolean)

        fun showComments(comments: MutableList<Comment>)

        fun updateTitle(commentsCount: Int)

        fun setEditorVisible(visible: Boolean, avatarUrl: String)

    }

    interface Presenter: BasePresenter {

        fun createComment(body: String)

        fun fetchComments()

    }

}