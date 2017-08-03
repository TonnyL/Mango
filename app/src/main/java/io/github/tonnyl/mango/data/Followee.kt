package io.github.tonnyl.mango.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/7/31.
 */
data class Followee(
        val id: Long,
        @SerializedName("created_at") val createAt: String,
        val followee: User
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(createAt)
        parcel.writeParcelable(followee, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Followee> {
        override fun createFromParcel(parcel: Parcel): Followee {
            return Followee(parcel)
        }

        override fun newArray(size: Int): Array<Followee?> {
            return arrayOfNulls(size)
        }
    }
}