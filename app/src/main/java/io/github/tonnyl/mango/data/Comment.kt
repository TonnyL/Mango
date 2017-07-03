package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 */
class Comment {

    @SerializedName("id")
    @Expose
    var id: Long = 0L

    @SerializedName("body")
    @Expose
    var body: String = ""

    @SerializedName("likes_count")
    @Expose
    var likesCount: Int = 0

    @SerializedName("likes_url")
    @Expose
    var likesUrl: String = ""

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