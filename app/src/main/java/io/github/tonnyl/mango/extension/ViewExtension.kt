package io.github.tonnyl.mango.extension

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.glide.GlideApp

/**
 * Load the avatar of users as circle cropped . Only png or jpg permitted.
 * If the url point to a gif, it will be loaded as bitmap.
 *
 * @param url The url of image.
 */
fun ImageView.loadAvatar(url: String?) {
    GlideApp.with(this)
            .asBitmap()
            .placeholder(R.drawable.ic_avatar_placeholder)
            .load(url)
            .circleCrop()
            .into(this)
}

/**
 * Load the image of a list. Gif and png both permitted. More advanced than [loadAvatar].
 *
 * @param url The url of image.
 */
fun ImageView.loadNormal(url: String) {
    if (url.endsWith(".gif")) {
        GlideApp.with(this)
                .asGif()
                .load(url)
                .centerCrop()
                .error(R.drawable.load_failed)
                .placeholder(R.drawable.ic_loading)
                .into(this)
    } else {
        GlideApp.with(this)
                .asBitmap()
                .load(url)
                .centerCrop()
                .error(R.drawable.load_failed)
                .placeholder(R.drawable.ic_loading)
                .into(this)
    }
}

/**
 * Load the image with best quality and get the [Palette] details.
 * Only the [Bitmap] has [Palette].
 *
 * @param url The url of image.
 * @param callback The [OnPaletteProcessCallback].
 */
fun ImageView.loadHighQualityWithPalette(url: String, callback: OnPaletteProcessCallback) {
    if (url.endsWith(".gif")) {
        GlideApp.with(this)
                .asGif()
                .load(url)
                .centerCrop()
                .error(R.drawable.load_failed)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_loading)
                .into(this)
    } else {
        GlideApp.with(this)
                .asBitmap()
                .load(url)
                .thumbnail(0.5f)
                .centerCrop()
                .error(R.drawable.load_failed)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_loading)
                .into(object : BitmapImageViewTarget(this) {

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

/**
 * A callback when [Palette] finished processing.
 */
interface OnPaletteProcessCallback {

    /**
     * The [Palette] finishes its work successfully.
     */
    fun OnPaletteGenerated(palette: Palette?)

    /**
     * The [Palette] finished its work with a failure.
     */
    fun OnPaletteNotAvailable()

}