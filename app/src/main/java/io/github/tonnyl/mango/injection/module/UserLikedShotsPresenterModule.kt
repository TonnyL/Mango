package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.user.likeshots.LikedShotsContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class UserLikedShotsPresenterModule(view: LikedShotsContract.View) {

    private val mView = view

    @Provides
    fun provideUserLikedShotsView(): LikedShotsContract.View {
        return mView
    }

}