package io.github.tonnyl.mango.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/26.
 */

@Entity(tableName = "access_token")
class AccessToken {

    @ColumnInfo(name = "access_token")
    @SerializedName("access_token")
    @Expose
    var accessToken: String = ""

    @ColumnInfo(name = "token_type")
    @SerializedName("token_type")
    @Expose
    var tokenType: String = ""

    @ColumnInfo(name = "scope")
    @SerializedName("scope")
    @Expose
    var scope: String = ""

    @ColumnInfo(name = "id")
    @field: PrimaryKey
    @Expose
    var id: Long = 0L
}