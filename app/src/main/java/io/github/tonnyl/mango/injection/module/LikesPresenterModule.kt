package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.shot.likes.LikesContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class LikesPresenterModule(view: LikesContract.View) {

    private val mView = view

    @Provides
    fun provideLikesView(): LikesContract.View {
        return mView
    }

}