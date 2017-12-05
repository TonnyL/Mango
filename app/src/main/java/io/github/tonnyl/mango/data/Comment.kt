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

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by lizhaotailang on 2017/6/30.
 *
 * {
 * "id" : 1145736,
 * "body" : "<p>Could he somehow make the shape of an \"S\" with his arms? I feel like i see potential for some hidden shapes in here...</p>\n\n<p>Looks fun!\n</p>",
 * "likes_count" : 1,
 * "likes_url" : "https://api.dribbble.com/v1/shots/471756/comments/1145736/likes",
 * "created_at" : "2012-03-15T04:24:39Z",
 * "updated_at" : "2012-03-15T04:24:39Z",
 * "user" : {
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
 * }
 */
data class Comment(

        @SerializedName("id")
        @Expose
        var id: Long,
        @SerializedName("body")
        @Expose
        var body: String,

        @SerializedName("likes_count")
        @Expose
        var likesCount: Int,

        @SerializedName("likes_url")
        @Expose
        var likesUrl: String,

        @SerializedName("created_at")
        @Expose
        var createdAt: Date,

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Date,

        @SerializedName("user")
        @Expose
        var user: User

) : Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            body = parcel.readString(),
            likesCount = parcel.readInt(),
            createdAt = Date(parcel.readLong()),
            likesUrl = parcel.readString(),
            updatedAt = Date(parcel.readLong()),
            user = parcel.readParcelable(User::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(body)
        parcel.writeInt(likesCount)
        parcel.writeString(likesUrl)
        parcel.writeLong(createdAt.time)
        parcel.writeLong(updatedAt.time)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }

}