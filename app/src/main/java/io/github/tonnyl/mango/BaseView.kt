package io.github.tonnyl.mango

/**
 * Created by lizhaotailang on 2017/6/24.
 */

interface BaseView<in T> {

    fun setPresenter(presenter : T)

}