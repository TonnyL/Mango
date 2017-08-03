package io.github.tonnyl.mango

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler

/**
 * Created by lizhaotailang on 2017/7/31.
 */

@DeepLinkHandler(MangoDeepLinkModule::class)
class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val deepLinkDelegate = DeepLinkDelegate(MangoDeepLinkModuleLoader())
        deepLinkDelegate.dispatchFrom(this)
        finish()

    }

}