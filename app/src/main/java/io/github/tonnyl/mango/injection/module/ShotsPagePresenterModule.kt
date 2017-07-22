package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.main.shots.ShotsPageContract

/**
 * Created by lizhaotailang on 2017/7/20.
 */

@Module
class ShotsPagePresenterModule(view: ShotsPageContract.View) {

    private val mView = view

    @Provides
    fun provideShotsPageView(): ShotsPageContract.View {
        return mView
    }

}