package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.shot.comments.CommentsContract

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class CommentsPresenterModule(view: CommentsContract.View) {

    private val mView = view

    @Provides
    fun provideCommentsView(): CommentsContract.View {
        return mView
    }

}