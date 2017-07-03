package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 */

class Team {

    @SerializedName("id")
    @Expose
    var id: Long = 0L

    @SerializedName("name")
    @Expose
    var name: String = ""

    @SerializedName("username")
    @Expose
    var username: String = ""

    @SerializedName("html_url")
    @Expose
    var htmlUrl: String = ""

    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String = ""

    @SerializedName("bio")
    @Expose
    var bio: String = ""

    @SerializedName("location")
    @Expose
    var location: String? = ""

    @SerializedName("links")
    @Expose
    var links: Links = Links()

    @SerializedName("buckets_count")
    @Expose
    var bucketsCount: Int = 0

    @SerializedName("comments_received_count")
    @Expose
    var commentsReceivedCount: Int = 0

    @SerializedName("followers_count")
    @Expose
    var followersCount: Int = 0

    @SerializedName("followings_count")
    @Expose
    var followingsCount: Int = 0

    @SerializedName("likes_count")
    @Expose
    var likesCount: Int = 0

    @SerializedName("likes_received_count")
    @Expose
    var likesReceivedCount: Int = 0

    @SerializedName("members_count")
    @Expose
    var membersCount: Int = 0

    @SerializedName("projects_count")
    @Expose
    var projectsCount: Int = 0

    @SerializedName("rebounds_received_count")
    @Expose
    var reboundsReceivedCount: Int = 0

    @SerializedName("shots_count")
    @Expose
    var shotsCount: Int = 0

    @SerializedName("can_upload_shot")
    @Expose
    var canUploadShot: Boolean = false

    @SerializedName("type")
    @Expose
    var type: String = ""

    @SerializedName("pro")
    @Expose
    var pro: Boolean = false

    @SerializedName("buckets_url")
    @Expose
    var bucketsUrl: String = ""

    @SerializedName("followers_url")
    @Expose
    var followersUrl: String = ""

    @SerializedName("following_url")
    @Expose
    var followingUrl: String = ""

    @SerializedName("likes_url")
    @Expose
    var likesUrl: String = ""

    @SerializedName("members_url")
    @Expose
    var membersUrl: String = ""

    @SerializedName("shots_url")
    @Expose
    var shotsUrl: String = ""

    @SerializedName("team_shots_url")
    @Expose
    var teamShotsUrl: String = ""

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""


}