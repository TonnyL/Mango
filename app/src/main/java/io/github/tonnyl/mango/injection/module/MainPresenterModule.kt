package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.main.MainContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class MainPresenterModule(view: MainContract.View) {

    private val mView = view

    @Provides
    fun provideMainView(): MainContract.View {
        return mView
    }

}