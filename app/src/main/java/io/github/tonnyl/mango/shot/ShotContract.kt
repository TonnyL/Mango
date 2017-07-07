package io.github.tonnyl.mango.shot

import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView
import io.github.tonnyl.mango.data.Shot

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface ShotContract {

    interface View : BaseView<Presenter> {

        fun isActive(): Boolean

        fun showMessage(message: String?)

        fun setLikeStatus(like: Boolean)

        fun updateLikeCount(count: Int)

        fun show(shot: Shot)

        fun navigateToUserProfile(userId: Long)

        fun navigateToComments(shotId: Long)

        fun navigateToBuckets(shotId: Long)

        fun navigateToLikes(shotId: Long)

        fun navigateToAttachments(shotId: Long)

    }

    interface Presenter : BasePresenter {

        fun setShotId(shotId: Long)

        fun toggleLike()

        fun navigateToUserProfile()

        fun navigateToComments()

        fun navigateToBuckets()

        fun navigateToLikes()

        fun navigateToAttachments()

    }

}