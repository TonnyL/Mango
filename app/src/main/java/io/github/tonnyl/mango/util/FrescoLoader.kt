package io.github.tonnyl.mango.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.graphics.Palette
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.core.ImagePipeline
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/1.
 */

object FrescoLoader {

    interface OnPaletteProcessCallback {

        fun OnPaletteAvailable(palette: Palette?)

        fun OnPaletteNotAvailable()

    }

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

    fun loadNormalWithPalette(context: Context, draweeView: SimpleDraweeView, urlNormal: String, urlLow: String?, callback: OnPaletteProcessCallback) {
        val builder = GenericDraweeHierarchyBuilder(context.applicationContext.resources)
        val hierarchy = builder.setPlaceholderImage(R.drawable.auth_background)
                .build()

        draweeView.hierarchy = hierarchy

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(true)
                .build()

        val source = Fresco.getImagePipeline().fetchDecodedImage(request, context)
        source.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                if (bitmap != null) {
                    Palette.from(bitmap).maximumColorCount(16).generate {
                        Palette.PaletteAsyncListener { palette ->
                            callback.OnPaletteAvailable(palette)
                        }
                    }
                } else {
                    callback.OnPaletteNotAvailable()
                }
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                callback.OnPaletteNotAvailable()
            }
        }, CallerThreadExecutor.getInstance())

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