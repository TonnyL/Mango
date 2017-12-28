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

package io.github.tonnyl.mango.data

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 2017/6/30.
 *
 * "images" : {
 * "hidpi" : null,
 * "normal" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png",
 * "teaser" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png"
 * }
 *
 * The normal image is typically 400x300, but may be smaller if created before October 4th, 2012.
 * The width and height provide the size of the normal image.
 * The hidpi image may or may not be present, but will always be 800x600.
 * The teaser image is typically 200x150, but may be smaller if created before October 4th, 2012.
 * If the animated attribute of the shot is true, the highest resolution image available (hidpi or normal)
 * will be animated (smaller images will be stills).
 */

@Parcelize
@SuppressLint("ParcelCreator")
data class Images(
        @SerializedName("hidpi")
        @Expose
        val hidpi: String?,

        @SerializedName("normal")
        @Expose
        val normal: String,

        @SerializedName("teaser")
        @Expose
        val teaser: String
) : Parcelable {

    fun best(): String {
        hidpi?.let {
            return it
        }
        return normal
    }

}