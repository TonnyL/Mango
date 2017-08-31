package io.github.tonnyl.mango.glide

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 31/08/2017.
 *
 * The global loader class for all the online images.
 */
object GlideLoader {

    /**
     * Load the avatar of users as circle cropped . Only png or jpg permitted.
     * If the url point to a gif, it will be loaded as bitmap.
     *
     * @param url The url of image.
     */
    fun loadAvatar(imageView: ImageView, url: String?) {
        GlideApp.with(imageView.context)
                .asBitmap()
                .placeholder(R.drawable.ic_avatar_placeholder)
                .load(url)
                .circleCrop()
                .error(R.drawable.ic_avatar_placeholder)
                .into(imageView)
    }

    /**
     * Load the images of a list. Gif and png both permitted.
     *
     * @param imageView The target [ImageView].
     * @param animationView The [LottieAnimationView] to display the loading animation.
     * @param url The url of image.
     */
    fun loadNormal(imageView: ImageView, animationView: LottieAnimationView, url: String) {
        if (url.endsWith(".gif")) {
            GlideApp.with(imageView.context)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .listener(object : RequestListener<GifDrawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                        override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }
                    })
                    .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                    .asBitmap()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .listener(object : RequestListener<Bitmap> {

                        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                    })
                    .into(imageView)
        }
    }

    /**
     * Load the image with best quality and get the [Palette] details.
     * Only the [Bitmap] has [Palette].
     *
     * @param imageView The target [ImageView].
     * @param animationView The [LottieAnimationView] to display the loading animation.
     * @param url The url of image.
     * @param callback The [OnPaletteProcessingCallback] when palette finishes processing.
     */
    fun loadHighQualityWithPalette(imageView: ImageView, animationView: LottieAnimationView, url: String, callback: OnPaletteProcessingCallback) {
        if (url.endsWith(".gif")) {
            GlideApp.with(imageView.context)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<GifDrawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                        override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                    })
                    .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                    .asBitmap()
                    .load(url)
                    .thumbnail(0.5f)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Bitmap> {

                        override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                            if (animationView.isAnimating) {
                                animationView.cancelAnimation()
                                animationView.visibility = View.GONE
                            }
                            return false
                        }

                    })
                    .into(object : BitmapImageViewTarget(imageView) {

                        // The function [onResourceReady] will called twice during one loading process.
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            super.onResourceReady(resource, transition)
                            resource?.let {
                                // The maximum color count is higher, the time of palette processing is more.
                                Palette.from(it).maximumColorCount(4).generate { palette ->
                                    callback.OnPaletteGenerated(palette)
                                }
                            } ?: run {
                                callback.OnPaletteNotAvailable()
                            }
                        }
                    })
        }
    }


}