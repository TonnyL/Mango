package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/7/4.
 */

class Attachment {

    @SerializedName("id")
    @Expose
    var id: Long = 0L

    @SerializedName("url")
    @Expose
    var url: String = ""

    @SerializedName("thumbnail_url")
    @Expose
    var thumbnailUrl: String = ""

    @SerializedName("size")
    @Expose
    var size: Int = 0

    @SerializedName("content_type")
    @Expose
    var contentType: String = ""

    @SerializedName("views_count")
    @Expose
    var viewsCount: Long = 0L

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

}