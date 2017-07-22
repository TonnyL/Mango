package io.github.tonnyl.mango.glide

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import io.github.tonnyl.mango.R

/**
 * Created by lizhaotailang on 2017/7/18.
 */

object GlideLoader {

    fun loadAvatar(context: Context, imageView: ImageView, url: String?) {
        GlideApp.with(context)
                .asBitmap()
                .placeholder(R.drawable.ic_avatar_placeholder)
                .load(url)
                .circleCrop()
                .into(imageView)
    }

    fun loadNormal(context: Context, imageView: ImageView, url: String) {
        if (url.endsWith(".gif")) {
            GlideApp.with(context)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.load_failed)
                    .placeholder(R.drawable.ic_loading)
                    .into(imageView)
        } else {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.load_failed)
                    .placeholder(R.drawable.ic_loading)
                    .into(imageView)
        }
    }

    fun loadHighQualityWithPalette(context: Context, imageView: ImageView, url: String, callback: OnPaletteProcessCallback) {
        if (url.endsWith(".gif")) {
            GlideApp.with(context)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.load_failed)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.ic_loading)
                    .into(imageView)
        } else {
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .thumbnail(0.5f)
                    .centerCrop()
                    .error(R.drawable.load_failed)
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.ic_loading)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            super.onResourceReady(resource, transition)
                            resource?.let {
                                Palette.from(it).maximumColorCount(8).generate { palette ->
                                    callback.OnPaletteGenerated(palette)
                                }
                            } ?: run {
                                callback.OnPaletteNotAvailable()
                            }
                        }
                    })
        }
    }

    interface OnPaletteProcessCallback {

        fun OnPaletteGenerated(palette: Palette?)

        fun OnPaletteNotAvailable()

    }

}