package io.github.tonnyl.mango.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 *
 * {
 * "hidpi" : null,
 * "normal" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png",
 * "teaser" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png"
 * }
 */

class Images() : Parcelable {

    @SerializedName("hidpi")
    @Expose
    var hidpi: String? = null

    @SerializedName("normal")
    @Expose
    var normal: String = ""

    @SerializedName("teaser")
    @Expose
    var teaser: String = ""

    constructor(parcel: Parcel) : this() {
        hidpi = parcel.readString()
        normal = parcel.readString()
        teaser = parcel.readString()
    }

    fun best(): String {
        hidpi?.let {
            return it
        }
        return normal
    }

    companion object CREATOR : Parcelable.Creator<Images> {
        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(hidpi)
        parcel.writeString(normal)
        parcel.writeString(teaser)
    }

    override fun describeContents(): Int {
        return 0
    }

}