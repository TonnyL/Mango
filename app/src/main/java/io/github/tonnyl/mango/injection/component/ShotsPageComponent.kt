package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.injection.module.ShotsPagePresenterModule
import io.github.tonnyl.mango.injection.scope.FragmentScoped
import io.github.tonnyl.mango.main.MainFragment

/**
 * Created by lizhaotailang on 2017/7/20.
 */

@FragmentScoped
@Component(dependencies = arrayOf(), modules = arrayOf(ShotsPagePresenterModule::class))
interface ShotsPageComponent {

    fun inject(fragment: MainFragment)

}