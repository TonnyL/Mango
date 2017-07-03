package io.github.tonnyl.mango.data

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/26.
 */

class Links {

    @ColumnInfo(name = "web")
    @SerializedName("web")
    @Expose
    var web: String = ""

    @ColumnInfo(name = "twitter")
    @SerializedName("twitter")
    @Expose
    var twitter: String = ""

}