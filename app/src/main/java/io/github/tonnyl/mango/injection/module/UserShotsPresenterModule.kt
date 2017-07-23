package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.user.shots.ShotsContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class UserShotsPresenterModule(view: ShotsContract.View) {

    private val mView = view

    @Provides
    fun provideUserShotsView(): ShotsContract.View {
        return mView
    }

}