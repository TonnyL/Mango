package io.github.tonnyl.mango.list.likes

import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView
import io.github.tonnyl.mango.data.Like

/**
 * Created by lizhaotailang on 2017/7/8.
 */
interface LikesContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showMessage(message: String?)

        fun showLikes(likes: MutableList<Like>)

    }

    interface Presenter: BasePresenter {

        fun fetchLikes()

    }

}