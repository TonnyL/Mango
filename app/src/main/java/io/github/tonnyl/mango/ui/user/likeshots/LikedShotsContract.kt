package io.github.tonnyl.mango.ui.user.likeshots

import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/19.
 *
 * This specifies the contract between the view and the presenter.
 */

interface LikedShotsContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(loading: Boolean)

        fun showShots(likeShots: MutableList<LikedShot>)

    }

    interface Presenter : BasePresenter

}