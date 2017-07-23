package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.github.tonnyl.mango.injection.module.ApplicationModule
import io.github.tonnyl.mango.injection.module.ShotsRepositoryModule
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Singleton
@Component(modules = arrayOf(ShotsRepositoryModule::class, ApplicationModule::class))
interface ShotsRepositoryComponent {

    fun getShotsRepository(): ShotsRepository

}