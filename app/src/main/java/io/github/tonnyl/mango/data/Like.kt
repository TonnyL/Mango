package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 */
class Like {

    @SerializedName("id")
    @Expose
    var id: Long = 0L

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("user")
    @Expose
    var user: User = User()

}