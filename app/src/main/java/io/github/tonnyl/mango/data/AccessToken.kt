package io.github.tonnyl.mango.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/26.
 *
 * {
 * "access_token" : "29ed478ab86c07f1c069b1af76088f7431396b7c4a2523d06911345da82224a0",
 * "token_type" : "bearer",
 * "scope" : "public write"
 * }
 */

@Entity(tableName = "access_token")
data class AccessToken(
        @ColumnInfo(name = "access_token")
        @SerializedName("access_token")
        @Expose
        var accessToken: String,

        @ColumnInfo(name = "token_type")
        @SerializedName("token_type")
        @Expose
        var tokenType: String,

        @ColumnInfo(name = "scope")
        @SerializedName("scope")
        @Expose
        var scope: String,

        // User id
        @PrimaryKey
        @ColumnInfo(name = "id")
        @Expose
        var id: Long
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessToken)
        parcel.writeString(tokenType)
        parcel.writeString(scope)
        parcel.writeLong(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<AccessToken> {
        override fun createFromParcel(parcel: Parcel): AccessToken {
            return AccessToken(parcel)
        }

        override fun newArray(size: Int): Array<AccessToken?> {
            return arrayOfNulls(size)
        }
    }

}