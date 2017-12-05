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

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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

data class Images(

        @SerializedName("hidpi")
        @Expose
        var hidpi: String?,

        @SerializedName("normal")
        @Expose
        var normal: String,

        @SerializedName("teaser")
        @Expose
        var teaser: String

) : Parcelable {

    constructor(parcel: Parcel) : this(
            hidpi = parcel.readString(),
            normal = parcel.readString(),
            teaser = parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hidpi)
        parcel.writeString(normal)
        parcel.writeString(teaser)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Images> {
        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }

    fun best(): String {
        hidpi?.let {
            return it
        }
        return normal
    }

}