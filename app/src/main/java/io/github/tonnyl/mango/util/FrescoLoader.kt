package io.github.tonnyl.mango.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.graphics.Palette
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
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

    fun loadNormal(context: Context, draweeView: SimpleDraweeView, urlNormal: String, urlLow: String?) {
        val builder = GenericDraweeHierarchyBuilder(context.applicationContext.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.drawable.auth_background)
                .setFailureImage(R.drawable.load_failed)
                .setRetryImage(R.drawable.load_failed)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()

        draweeView.hierarchy = hierarchy

        val request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(!urlNormal.endsWith(".gif"))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(false)
                .setUri(Uri.parse(urlNormal))
                .setLowResImageRequest(ImageRequest.fromUri(urlLow))
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()

        draweeView.controller = controller
    }

    fun loadNormalWithPalette(context: Context, draweeView: SimpleDraweeView, urlNormal: String, urlLow: String?, callback: OnPaletteProcessCallback) {
        val builder = GenericDraweeHierarchyBuilder(context.applicationContext.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.drawable.auth_background)
                .setFailureImage(R.drawable.load_failed)
                .setRetryImage(R.drawable.load_failed)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()

        draweeView.hierarchy = hierarchy

        val request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(!urlNormal.endsWith(".gif"))
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse(urlNormal))
                .setLowResImageRequest(ImageRequest.fromUri(urlLow))
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(draweeView.controller)
                .build()

        draweeView.controller = controller

        Fresco.getImagePipeline().fetchDecodedImage(request, draweeView.context).subscribe(object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                bitmap?.let {
                    Palette.from(bitmap).maximumColorCount(8).generate { palette ->
                        callback.OnPaletteGenerated(palette)
                    }
                } ?: run {
                    callback.OnPaletteNotAvailable()
                }
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                callback.OnPaletteNotAvailable()
            }

        }, CallerThreadExecutor.getInstance())

    }

    interface OnPaletteProcessCallback {

        fun OnPaletteGenerated(palette: Palette?)

        fun OnPaletteNotAvailable()

    }

}