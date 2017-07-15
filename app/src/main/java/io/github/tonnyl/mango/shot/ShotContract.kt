package io.github.tonnyl.mango.shot

import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView
import io.github.tonnyl.mango.data.Shot

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface ShotContract {

    interface View : BaseView<Presenter> {

        fun showMessage(message: String?)

        fun setLikeStatus(like: Boolean)

        fun updateLikeCount(count: Int)

        fun show(shot: Shot)

        fun navigateToUserProfile(userId: Long)

        fun navigateToComments(shotId: Long)

        fun navigateToLikes(shotId: Long)

    }

    interface Presenter : BasePresenter {

        fun setShot(shot: Shot)

        fun toggleLike()

        fun navigateToUserProfile()

        fun navigateToComments()

        fun navigateToLikes()

    }

}