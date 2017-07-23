package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.injection.scope.ActivityScoped
import io.github.tonnyl.mango.user.UserProfileActivity

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@ActivityScoped
@Component(dependencies = arrayOf(), modules = arrayOf())
interface UserComponent {

    fun inject(activity: UserProfileActivity)

}