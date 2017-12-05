/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
data class User(
        @ColumnInfo(name = "name")
        @SerializedName("name")
        @Expose
        var name: String,

        @ColumnInfo(name = "username")
        @SerializedName("username")
        @Expose
        var username: String,

        @ColumnInfo(name = "html_url")
        @SerializedName("html_url")
        @Expose
        var htmlUrl: String,

        @ColumnInfo(name = "avatar_url")
        @SerializedName("avatar_url")
        @Expose
        var avatarUrl: String,

        @ColumnInfo(name = "bio")
        @SerializedName("bio")
        @Expose
        var bio: String,

        @ColumnInfo(name = "location")
        @SerializedName("location")
        @Expose
        var location: String?,

        @Embedded
        @SerializedName("links")
        @Expose
        var links: Links,

        @ColumnInfo(name = "buckets_count")
        @SerializedName("buckets_count")
        @Expose
        var bucketsCount: Int,

        @ColumnInfo(name = "comments_received_count")
        @SerializedName("comments_received_count")
        @Expose
        var commentsReceivedCount: Int,

        @ColumnInfo(name = "followers_count")
        @SerializedName("followers_count")
        @Expose
        var followersCount: Int,

        @ColumnInfo(name = "followings_count")
        @SerializedName("followings_count")
        @Expose
        var followingsCount: Int,

        @ColumnInfo(name = "likes_count")
        @SerializedName("likes_count")
        @Expose
        var likesCount: Int,

        @ColumnInfo(name = "likes_received_count")
        @SerializedName("likes_received_count")
        @Expose
        var likesReceivedCount: Int,

        @ColumnInfo(name = "projects_count")
        @SerializedName("projects_count")
        @Expose
        var projectsCount: Int,

        @ColumnInfo(name = "rebounds_received_count")
        @SerializedName("rebounds_received_count")
        @Expose
        var reboundsReceivedCount: Int,

        @ColumnInfo(name = "shots_count")
        @SerializedName("shots_count")
        @Expose
        var shotsCount: Int,

        @ColumnInfo(name = "teams_count")
        @SerializedName("teams_count")
        @Expose
        var teamsCount: Int,

        @ColumnInfo(name = "can_upload_shot")
        @SerializedName("can_upload_shot")
        @Expose
        var canUploadShot: Boolean,

        @ColumnInfo(name = "type")
        @SerializedName("type")
        @Expose
        var type: String,

        @ColumnInfo(name = "pro")
        @SerializedName("pro")
        @Expose
        var pro: Boolean,

        @ColumnInfo(name = "buckets_url")
        @SerializedName("buckets_url")
        @Expose
        var bucketsUrl: String,

        @ColumnInfo(name = "followers_url")
        @SerializedName("followers_url")
        @Expose
        var followersUrl: String,

        @ColumnInfo(name = "following_url")
        @SerializedName("following_url")
        @Expose
        var followingUrl: String,

        @ColumnInfo(name = "likes_url")
        @SerializedName("likes_url")
        @Expose
        var likesUrl: String,

        @ColumnInfo(name = "shots_url")
        @SerializedName("shots_url")
        @Expose
        var shotsUrl: String,

        @ColumnInfo(name = "teams_url")
        @SerializedName("teams_url")
        @Expose
        var teamsUrl: String?,

        @ColumnInfo(name = "created_at")
        @SerializedName("created_at")
        @Expose
        var createdAt: Date,

        @ColumnInfo(name = "updated_at")
        @SerializedName("updated_at")
        @Expose
        var updatedAt: Date,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        @Expose
        var id: Long

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Links::class.java.classLoader),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            Date(parcel.readLong()),
            Date(parcel.readLong()),
            parcel.readLong())

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