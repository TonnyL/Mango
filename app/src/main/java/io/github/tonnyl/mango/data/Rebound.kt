package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 */

class Rebound {

    @SerializedName("id")
    var id: Long = 0L

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("description")
    @Expose
    var description: String? = ""

    @SerializedName("width")
    @Expose
    var width: Int = 0

    @SerializedName("height")
    @Expose
    var height: Int = 0

    @SerializedName("images")
    @Expose
    var images: Images = Images()

    @SerializedName("views_count")
    @Expose
    var viewsCount: Int = 0

    @SerializedName("likes_count")
    @Expose
    var likesCount: Int = 0

    @SerializedName("comments_count")
    @Expose
    var commentsCount: Int = 0

    @SerializedName("attachments_count")
    @Expose
    var attachmentsCount: Int = 0

    @SerializedName("rebounds_count")
    @Expose
    var reboundsCount: Int = 0

    @SerializedName("buckets_count")
    @Expose
    var bucketsCount: Int = 0

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""

    @SerializedName("html_url")
    @Expose
    var htmlUrl: String = ""

    @SerializedName("attachments_url")
    @Expose
    var attachmentsUrl: String = ""

    @SerializedName("comments_url")
    @Expose
    var commentsUrl: String = ""

    @SerializedName("likes_url")
    @Expose
    var likesUrl: String = ""

    @SerializedName("projects_url")
    @Expose
    var projectsUrl: String = ""

    @SerializedName("rebounds_url")
    @Expose
    var reboundsUrl: String = ""

    @SerializedName("rebound_source_url")
    @Expose
    var reboundSourceUrl: String = ""

    @SerializedName("tags")
    @Expose
    var tags: List<String> = emptyList()

    @SerializedName("user")
    @Expose
    var user: User = User()

    @SerializedName("team")
    @Expose
    var team: Team = Team()

}