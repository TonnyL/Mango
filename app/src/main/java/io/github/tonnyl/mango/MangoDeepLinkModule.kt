package io.github.tonnyl.mango

import com.airbnb.deeplinkdispatch.DeepLinkModule

/**
 * Created by lizhaotailang on 2017/7/31.
 *
 * The deep link module. For every class annotated [DeepLinkModule],
 * [DeepLinkDispatch](com.airbnb.deeplinkdispatch) will generate a `Loader` class,
 * which contains a registry of all the [DeepLinkModule] annotations.
 */

@DeepLinkModule
class MangoDeepLinkModule