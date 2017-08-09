package io.github.tonnyl.mango.ui.shot.likes

import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/8.
 *
 * This specifies the contract between the view and the presenter.
 */
interface LikesContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showMessage(message: String?)

        fun showLikes(likes: MutableList<Like>)

        fun updateTitle(likeCount: Int)

    }

    interface Presenter: BasePresenter {

        fun fetchLikes()

    }

}