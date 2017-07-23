package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.auth.AuthActivity
import io.github.tonnyl.mango.injection.scope.ActivityScoped

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@ActivityScoped
@Component(dependencies = arrayOf(), modules = arrayOf())
interface AuthComponent {

    fun inject(activity: AuthActivity)

}