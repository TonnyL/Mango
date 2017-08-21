package io.github.tonnyl.mango.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by lizhaotailang on 2017/6/30.
 *
 * {
 * "id" : 39,
 * "name" : "Dribbble",
 * "username" : "dribbble",
 * "html_url" : "https://dribbble.com/dribbble",
 * "avatar_url" : "https://d13yacurqjgara.cloudfront.net/users/39/avatars/normal/apple-flat-precomposed.png?1388527574",
 * "bio" : "Show and tell for designers. This is Dribbble on Dribbble.",
 * "location" : "Salem, MA",
 * "links" : {
 * "web" : "http://dribbble.com",
 * "twitter" : "https://twitter.com/dribbble"
 * },
 * "buckets_count" : 1,
 * "comments_received_count" : 2037,
 * "followers_count" : 25011,
 * "followings_count" : 6120,
 * "likes_count" : 44,
 * "likes_received_count" : 15811,
 * "members_count" : 7,
 * "projects_count" : 4,
 * "rebounds_received_count" : 416,
 * "shots_count" : 91,
 * "can_upload_shot" : true,
 * "type" : "Team",
 * "pro" : false,
 * "buckets_url" : "https://dribbble.com/v1/users/39/buckets",
 * "followers_url" : "https://dribbble.com/v1/users/39/followers",
 * "following_url" : "https://dribbble.com/v1/users/39/following",
 * "likes_url" : "https://dribbble.com/v1/users/39/likes",
 * "members_url" : "https://dribbble.com/v1/teams/39/members",
 * "shots_url" : "https://dribbble.com/v1/users/39/shots",
 * "team_shots_url" : "https://dribbble.com/v1/users/39/teams",
 * "created_at" : "2009-08-18T18:34:31Z",
 * "updated_at" : "2014-02-14T22:32:11Z"
 * }
 */

data class Team(
        @SerializedName("id")
        @Expose
        var id: Long,

        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("username")
        @Expose
        var username: String,

        @SerializedName("html_url")
        @Expose
        var htmlUrl: String,

        @SerializedName("avatar_url")
        @Expose
        var avatarUrl: String,

        @SerializedName("bio")
        @Expose
        var bio: String,

        @SerializedName("location")
        @Expose
        var location: String?,

        @SerializedName("links")
        @Expose
        var links: Links,

        @SerializedName("buckets_count")
        @Expose
        var bucketsCount: Int,

        @SerializedName("comments_received_count")
        @Expose
        var commentsReceivedCount: Int,

        @SerializedName("followers_count")
        @Expose
        var followersCount: Int,

        @SerializedName("followings_count")
        @Expose
        var followingsCount: Int,

        @SerializedName("likes_count")
        @Expose
        var likesCount: Int,

        @SerializedName("likes_received_count")
        @Expose
        var likesReceivedCount: Int,

        @SerializedName("members_count")
        @Expose
        var membersCount: Int,

        @SerializedName("projects_count")
        @Expose
        var projectsCount: Int,

        @SerializedName("rebounds_received_count")
        @Expose
        var reboundsReceivedCount: Int,

        @SerializedName("shots_count")
        @Expose
        var shotsCount: Int,

        @SerializedName("can_upload_shot")
        @Expose
        var canUploadShot: Boolean,

        @SerializedName("type")
        @Expose
        var type: String,

        @SerializedName("pro")
        @Expose
        var pro: Boolean,

        @SerializedName("buckets_url")
        @Expose
        var bucketsUrl: String,

        @SerializedName("followers_url")
        @Expose
        var followersUrl: String,

        @SerializedName("following_url")
        @Expose
        var followingUrl: String,

        @SerializedName("likes_url")
        @Expose
        var likesUrl: String,

        @SerializedName("members_url")
        @Expose
        var membersUrl: String,

        @SerializedName("shots_url")
        @Expose
        var shotsUrl: String,

        @SerializedName("team_shots_url")
        @Expose
        var teamShotsUrl: String,

        @SerializedName("created_at")
        @Expose
        var createdAt: Date,

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Date
) : Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            name = parcel.readString(),
            username = parcel.readString(),
            htmlUrl = parcel.readString(),
            avatarUrl = parcel.readString(),
            bio = parcel.readString(),
            location = parcel.readString(),
            links = parcel.readParcelable(Links::class.java.classLoader),
            bucketsCount = parcel.readInt(),
            commentsReceivedCount = parcel.readInt(),
            followersCount = parcel.readInt(),
            followingsCount = parcel.readInt(),
            likesCount = parcel.readInt(),
            likesReceivedCount = parcel.readInt(),
            membersCount = parcel.readInt(),
            projectsCount = parcel.readInt(),
            reboundsReceivedCount = parcel.readInt(),
            shotsCount = parcel.readInt(),
            canUploadShot = parcel.readByte() != 0.toByte(),
            type = parcel.readString(),
            pro = parcel.readByte() != 0.toByte(),
            bucketsUrl = parcel.readString(),
            followersUrl = parcel.readString(),
            followingUrl = parcel.readString(),
            likesUrl = parcel.readString(),
            membersUrl = parcel.readString(),
            shotsUrl = parcel.readString(),
            teamShotsUrl = parcel.readString(),
            createdAt = Date(parcel.readLong()),
            updatedAt = Date(parcel.readLong())
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(htmlUrl)
        parcel.writeString(avatarUrl)
        parcel.writeString(bio)
        parcel.writeString(location)
        parcel.writeParcelable(links, flags)
        parcel.writeInt(bucketsCount)
        parcel.writeInt(commentsReceivedCount)
        parcel.writeInt(followersCount)
        parcel.writeInt(followingsCount)
        parcel.writeInt(likesCount)
        parcel.writeInt(likesReceivedCount)
        parcel.writeInt(membersCount)
        parcel.writeInt(projectsCount)
        parcel.writeInt(reboundsReceivedCount)
        parcel.writeInt(shotsCount)
        parcel.writeByte(if (canUploadShot) 1.toByte() else 0.toByte())
        parcel.writeString(type)
        parcel.writeByte(if (pro) 1.toByte() else 0.toByte())
        parcel.writeString(bucketsUrl)
        parcel.writeString(followersUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(likesUrl)
        parcel.writeString(membersUrl)
        parcel.writeString(shotsUrl)
        parcel.writeString(teamShotsUrl)
        parcel.writeLong(createdAt.time)
        parcel.writeLong(updatedAt.time)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }

}