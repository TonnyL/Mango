package io.github.tonnyl.mango.shot

import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface ShotContract {

    interface View : BaseView<Presenter> {

        fun showMessage(message: String?)

        fun setLikeStatus(like: Boolean)

        fun updateLikeCount(count: Int)

        fun show(shot: Shot)

        fun navigateToUserProfile(user: User)

        fun navigateToComments(shot: Shot)

        fun navigateToLikes(shot: Shot)

    }

    interface Presenter : BasePresenter {

        fun toggleLike()

        fun navigateToUserProfile()

        fun navigateToComments()

        fun navigateToLikes()

    }

}