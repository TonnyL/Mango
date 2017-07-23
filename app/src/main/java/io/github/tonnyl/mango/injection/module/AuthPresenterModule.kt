package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.auth.AuthContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class AuthPresenterModule(view: AuthContract.View) {

    private val mView = view

    @Provides
    fun provideAuthView(): AuthContract.View {
        return mView
    }

}