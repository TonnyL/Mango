package io.github.tonnyl.mango.injection.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by lizhaotailang on 2017/7/20.
 */

@Module
class ApplicationModule(context: Context) {

    private val mContext = context

    @Provides
    fun provideContext(): Context {
        return mContext
    }

}