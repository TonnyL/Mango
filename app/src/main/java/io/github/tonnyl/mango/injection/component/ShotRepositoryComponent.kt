package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.data.repository.ShotRepository
import io.github.tonnyl.mango.injection.module.ApplicationModule
import io.github.tonnyl.mango.injection.module.ShotRepositoryModule
import javax.inject.Singleton

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@Singleton
@Component(modules = arrayOf(ShotRepositoryModule::class, ApplicationModule::class))
interface ShotRepositoryComponent {

    fun getShotRepository(): ShotRepository

}