/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.glide

import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.request.target.BitmapImageViewTarget
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
     * @param url The url of image.
     */
    fun loadNormal(imageView: ImageView, url: String) {
        if (url.endsWith(".gif")) {
            GlideApp.with(imageView.context)
                    .asGif()
                    .load(url)
                    .placeholder(R.drawable.bg_shot)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.bg_shot)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .into(imageView)
        }
    }

    /**
     * Load the image with best quality and get the [Palette] details.
     * Only the [Bitmap] has [Palette].
     *
     * @param imageView The target [ImageView].
     * @param url The url of image.
     * @param callback The [OnPaletteProcessingCallback] when palette finishes processing.
     */
    fun loadHighQualityWithPalette(imageView: ImageView, url: String, callback: OnPaletteProcessingCallback) {
        if (url.endsWith(".gif")) {
            GlideApp.with(imageView.context)
                    .asGif()
                    .load(url)
                    .placeholder(R.drawable.bg_shot)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .priority(Priority.HIGH)
                    .into(imageView)
        } else {
            GlideApp.with(imageView.context)
                    .asBitmap()
                    .load(url)
                    .placeholder(R.drawable.bg_shot)
                    .thumbnail(0.5f)
                    .centerCrop()
                    .error(R.drawable.bg_shot)
                    .priority(Priority.HIGH)
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