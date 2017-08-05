package io.github.tonnyl.mango.ui.settings

import android.content.Context
import io.github.tonnyl.mango.mvp.BasePresenter
import io.github.tonnyl.mango.mvp.BaseView

/**
 * Created by lizhaotailang on 2017/7/19.
 */

interface SettingsContract {

    interface View : BaseView<Presenter> {

        fun updateCacheSize(size: Long)

    }

    interface Presenter : BasePresenter {

        fun computeCacheSize(context: Context)

        fun clearCache(context: Context)

    }

}