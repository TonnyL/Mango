package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.injection.module.ShotPresenterModule
import io.github.tonnyl.mango.injection.scope.ActivityScoped
import io.github.tonnyl.mango.shot.ShotActivity

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@ActivityScoped
@Component(dependencies = arrayOf(ShotRepositoryComponent::class), modules = arrayOf(ShotPresenterModule::class))
interface ShotComponent {

    fun inject(activity: ShotActivity)

}