package io.github.tonnyl.mango.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/7/20.
 */
class LikedShot() : Parcelable {

    @SerializedName("id")
    @Expose
    var id = 0L

    @SerializedName("created_at")
    @Expose
    var createdAt = ""

    @SerializedName("shot")
    @Expose
    var shot = Shot()

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        createdAt = parcel.readString()
        shot = parcel.readParcelable(Shot::class.java.classLoader)
    }

    companion object CREATOR : Parcelable.Creator<LikedShot> {
        override fun createFromParcel(parcel: Parcel): LikedShot {
            return LikedShot(parcel)
        }

        override fun newArray(size: Int): Array<LikedShot?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeLong(id)
        parcel?.writeString(createdAt)
        parcel?.writeParcelable(shot, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

}