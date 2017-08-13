package io.github.tonnyl.mango.data

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.github.tonnyl.mango.database.converter.DateConverter
import java.util.*

/**
 * Created by lizhaotailang on 2017/6/25.
 *
 * {
 * "id" : 1,
 * "name" : "Dan Cederholm",
 * "username" : "simplebits",
 * "html_url" : "https://dribbble.com/simplebits",
 * "avatar_url" : "https://d13yacurqjgara.cloudfront.net/users/1/avatars/normal/dc.jpg?1371679243",
 * "bio" : "Co-founder &amp; designer of <a href=\"https://dribbble.com/dribbble\">@Dribbble</a>. Principal of SimpleBits. Aspiring clawhammer banjoist.",
 * "location" : "Salem, MA",
 * "links" : {
 * "web" : "http://simplebits.com",
 * "twitter" : "https://twitter.com/simplebits"
 * },
 * "buckets_count" : 10,
 * "comments_received_count" : 3395,
 * "followers_count" : 29262,
 * "followings_count" : 1728,
 * "likes_count" : 34954,
 * "likes_received_count" : 27568,
 * "projects_count" : 8,
 * "rebounds_received_count" : 504,
 * "shots_count" : 214,
 * "teams_count" : 1,
 * "can_upload_shot" : true,
 * "type" : "Player",
 * "pro" : true,
 * "buckets_url" : "https://dribbble.com/v1/users/1/buckets",
 * "followers_url" : "https://dribbble.com/v1/users/1/followers",
 * "following_url" : "https://dribbble.com/v1/users/1/following",
 * "likes_url" : "https://dribbble.com/v1/users/1/likes",
 * "shots_url" : "https://dribbble.com/v1/users/1/shots",
 * "teams_url" : "https://dribbble.com/v1/users/1/teams",
 * "created_at" : "2009-07-08T02:51:22Z",
 * "updated_at" : "2014-02-22T17:10:33Z"
 * }
 */

@Entity(tableName = "user")
@TypeConverters(DateConverter::class)
class User() : Parcelable {

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String = ""

    @ColumnInfo(name = "username")
    @SerializedName("username")
    @Expose
    var username: String = ""

    @ColumnInfo(name = "html_url")
    @SerializedName("html_url")
    @Expose
    var htmlUrl: String = ""

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String = ""

    @ColumnInfo(name = "bio")
    @SerializedName("bio")
    @Expose
    var bio: String = ""

    @ColumnInfo(name = "location")
    @SerializedName("location")
    @Expose
    var location: String? = ""

    @Embedded
    @SerializedName("links")
    @Expose
    var links: Links = Links()

    @ColumnInfo(name = "buckets_count")
    @SerializedName("buckets_count")
    @Expose
    var bucketsCount: Int = 0

    @ColumnInfo(name = "comments_received_count")
    @SerializedName("comments_received_count")
    @Expose
    var commentsReceivedCount: Int = 0

    @ColumnInfo(name = "followers_count")
    @SerializedName("followers_count")
    @Expose
    var followersCount: Int = 0

    @ColumnInfo(name = "followings_count")
    @SerializedName("followings_count")
    @Expose
    var followingsCount: Int = 0

    @ColumnInfo(name = "likes_count")
    @SerializedName("likes_count")
    @Expose
    var likesCount: Int = 0

    @ColumnInfo(name = "likes_received_count")
    @SerializedName("likes_received_count")
    @Expose
    var likesReceivedCount: Int = 0

    @ColumnInfo(name = "projects_count")
    @SerializedName("projects_count")
    @Expose
    var projectsCount: Int = 0

    @ColumnInfo(name = "rebounds_received_count")
    @SerializedName("rebounds_received_count")
    @Expose
    var reboundsReceivedCount: Int = 0

    @ColumnInfo(name = "shots_count")
    @SerializedName("shots_count")
    @Expose
    var shotsCount: Int = 0

    @ColumnInfo(name = "teams_count")
    @SerializedName("teams_count")
    @Expose
    var teamsCount: Int = 0

    @ColumnInfo(name = "can_upload_shot")
    @SerializedName("can_upload_shot")
    @Expose
    var canUploadShot: Boolean = false

    @ColumnInfo(name = "type")
    @SerializedName("type")
    @Expose
    var type: String = ""

    @ColumnInfo(name = "pro")
    @SerializedName("pro")
    @Expose
    var pro: Boolean = false

    @ColumnInfo(name = "buckets_url")
    @SerializedName("buckets_url")
    @Expose
    var bucketsUrl: String = ""

    @ColumnInfo(name = "followers_url")
    @SerializedName("followers_url")
    @Expose
    var followersUrl: String = ""

    @ColumnInfo(name = "following_url")
    @SerializedName("following_url")
    @Expose
    var followingUrl: String = ""

    @ColumnInfo(name = "likes_url")
    @SerializedName("likes_url")
    @Expose
    var likesUrl: String = ""

    @ColumnInfo(name = "shots_url")
    @SerializedName("shots_url")
    @Expose
    var shotsUrl: String = ""

    @ColumnInfo(name = "teams_url")
    @SerializedName("teams_url")
    @Expose
    var teamsUrl: String? = ""

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    @Expose
    var createdAt: Date = Date()

    @ColumnInfo(name = "updated_at")
    @SerializedName("updated_at")
    @Expose
    var updatedAt: Date = Date()

    @ColumnInfo(name = "id")
    @field: PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Long = 0L

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        username = parcel.readString()
        htmlUrl = parcel.readString()
        avatarUrl = parcel.readString()
        bio = parcel.readString()
        location = parcel.readString()
        links = parcel.readParcelable(Links::class.java.classLoader)
        bucketsCount = parcel.readInt()
        commentsReceivedCount = parcel.readInt()
        followersCount = parcel.readInt()
        followingsCount = parcel.readInt()
        likesCount = parcel.readInt()
        likesReceivedCount = parcel.readInt()
        projectsCount = parcel.readInt()
        reboundsReceivedCount = parcel.readInt()
        shotsCount = parcel.readInt()
        teamsCount = parcel.readInt()
        canUploadShot = parcel.readByte() != 0.toByte()
        type = parcel.readString()
        pro = parcel.readByte() != 0.toByte()
        bucketsUrl = parcel.readString()
        followersUrl = parcel.readString()
        followingUrl = parcel.readString()
        likesUrl = parcel.readString()
        shotsUrl = parcel.readString()
        teamsUrl = parcel.readString()
        createdAt = Date(parcel.readLong())
        updatedAt = Date(parcel.readLong())
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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
        parcel.writeInt(projectsCount)
        parcel.writeInt(reboundsReceivedCount)
        parcel.writeInt(shotsCount)
        parcel.writeInt(teamsCount)
        parcel.writeByte(if (canUploadShot) 1.toByte() else 0.toByte())
        parcel.writeString(type)
        parcel.writeByte(if (pro) 1.toByte() else 0.toByte())
        parcel.writeString(bucketsUrl)
        parcel.writeString(followersUrl)
        parcel.writeString(followingUrl)
        parcel.writeString(likesUrl)
        parcel.writeString(shotsUrl)
        parcel.writeString(teamsUrl)
        parcel.writeLong(createdAt.time)
        parcel.writeLong(updatedAt.time)
        parcel.writeLong(id)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}