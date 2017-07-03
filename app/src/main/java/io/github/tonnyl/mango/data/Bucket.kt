package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 */

class Bucket {

    @SerializedName("id")
    @Expose
    var id: Long = 0L

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("description")
    @Expose
    var description: String = ""

    @SerializedName("shots_count")
    @Expose
    var shotsCount: Int = 0

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""

    @SerializedName("user")
    @Expose
    var user: User = User()

}