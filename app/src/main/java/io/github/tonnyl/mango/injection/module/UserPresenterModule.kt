package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.user.UserProfileContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class UserPresenterModule(view: UserProfileContract.View) {

    private val mView = view

    @Provides
    fun provideUserView(): UserProfileContract.View {
        return mView
    }

}