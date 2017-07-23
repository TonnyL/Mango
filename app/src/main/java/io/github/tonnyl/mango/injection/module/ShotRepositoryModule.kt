package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.data.repository.ShotRepository
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class ShotRepositoryModule {

    @Singleton
    @Provides
    fun provideShotRepository(): ShotRepository {
        return ShotRepository
    }

}