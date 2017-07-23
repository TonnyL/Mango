package io.github.tonnyl.mango.injection.component

import dagger.Component
import io.github.tonnyl.mango.injection.scope.FragmentScoped
import io.github.tonnyl.mango.user.UserProfileFragment

/**
 * Created by lizhaotailang on 2017/7/23.
 */

@FragmentScoped
@Component(dependencies = arrayOf(), modules = arrayOf())
interface UserShotsComponent {

    fun inject(fragment: UserProfileFragment)

}