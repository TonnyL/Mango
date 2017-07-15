package io.github.tonnyl.mango.list.comments

import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView
import io.github.tonnyl.mango.data.Comment

/**
 * Created by lizhaotailang on 2017/7/8.
 */
interface CommentsContract {

    interface View: BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showMessage(message: String?)

        fun cancelSendingIndicator(clearText: Boolean)

        fun showComments(comments: MutableList<Comment>)

    }

    interface Presenter: BasePresenter {

        fun createComment(body: String)

        fun fetchComments()

    }

}