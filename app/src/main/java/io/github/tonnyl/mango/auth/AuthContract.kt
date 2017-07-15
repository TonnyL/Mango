package io.github.tonnyl.mango.auth

import android.content.Intent
import android.support.annotation.StringRes
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView
import io.github.tonnyl.mango.data.AccessToken

/**
 * Created by lizhaotailang on 2017/6/24.
 */

interface AuthContract {

    interface View: BaseView<Presenter> {

        fun isActive(): Boolean

        fun setLoginIndicator(isLoading: Boolean)

        fun showMessage(@StringRes id: Int)

        fun showMessage(message: String)

        fun handleAuthCallback(intent: Intent?)

        fun updateLoginStatus(accessToken: AccessToken)

    }

    interface Presenter: BasePresenter {

        fun requestAccessToken(code: String)

    }

}