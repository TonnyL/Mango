package io.github.tonnyl.mango.mvp

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * Base view in MVP architecture.
 */

interface BaseView<in T> {

    fun setPresenter(presenter : T)

}