package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.shot.ShotContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class ShotPresenterModule(view: ShotContract.View) {

    private val mView = view

    @Provides
    fun provideShotView(): ShotContract.View {
        return mView
    }

}