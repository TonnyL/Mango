package io.github.tonnyl.mango

import android.app.Application
import io.github.tonnyl.mango.data.repository.AccessTokenRepository

/**
 * Created by lizhaotailang on 2017/6/24.
 */

class MangoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AccessTokenRepository.init(this)
    }

}