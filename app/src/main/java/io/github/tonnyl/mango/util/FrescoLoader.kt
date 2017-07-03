package io.github.tonnyl.mango.util

import android.content.Context
import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/1.
 */

object FrescoLoader {

    fun loadAvatar(draweeView: SimpleDraweeView, url: String) {
        draweeView.setImageURI(url)
    }

    fun loadNormal(context: Context, draweeView: SimpleDraweeView, urlNormal: String, urlLow: String?, animate: Boolean = true) {
        val builder = GenericDraweeHierarchyBuilder(context.applicationContext.resources)
        val hierarchy = builder.setPlaceholderImage(R.drawable.auth_background)
                .build()

        draweeView.hierarchy = hierarchy

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(true)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse(urlNormal))
                .setLowResImageRequest(ImageRequest.fromUri(urlLow))
                // .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()

        draweeView.controller = controller
    }

}