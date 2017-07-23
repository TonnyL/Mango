package io.github.tonnyl.mango.injection.module

import dagger.Module
import dagger.Provides
import io.github.tonnyl.mango.data.repository.ShotsRepository
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Module
class ShotsRepositoryModule {

    @Provides
    @Singleton
    fun provideShotsRepository(): ShotsRepository {
        return ShotsRepository
    }

}