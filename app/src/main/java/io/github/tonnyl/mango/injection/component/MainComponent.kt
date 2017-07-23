package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.injection.module.MainPresenterModule
import io.github.tonnyl.mango.injection.scope.ActivityScoped
import io.github.tonnyl.mango.main.MainActivity

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@ActivityScoped
@Component(dependencies = arrayOf(), modules = arrayOf(MainPresenterModule::class))
interface MainComponent {

    fun inject(activity: MainActivity)

}