package io.github.tonnyl.mango.data

import android.arch.persistence.room.ColumnInfo
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/26.
 */

class Links() : Parcelable {

    @ColumnInfo(name = "web")
    @SerializedName("web")
    @Expose
    var web: String = ""

    @ColumnInfo(name = "twitter")
    @SerializedName("twitter")
    @Expose
    var twitter: String = ""

    constructor(parcel: Parcel) : this() {
        web = parcel.readString()
        twitter = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(web)
        parcel.writeString(twitter)
    }

    override fun describeContents(): Int {
        return 0
    }

}