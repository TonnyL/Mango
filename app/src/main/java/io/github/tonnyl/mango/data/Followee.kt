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

/**
 * Created by lizhaotailang on 2017/7/31.
 *
 * {
 * "id" : 9843830,
 * "created_at" : "2014-02-21T15:41:14Z",
 * "followee" : {
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

data class Followee(

        @SerializedName("id")
        @Expose
        val id: Long,

        @SerializedName("created_at")
        @Expose
        val createAt: String,

        @SerializedName("followee")
        @Expose
        val followee: User

) : Parcelable {

    constructor(parcel: Parcel) : this(
            id = parcel.readLong(),
            createAt = parcel.readString(),
            followee = parcel.readParcelable(User::class.java.classLoader))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(createAt)
        parcel.writeParcelable(followee, flags)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Followee> {
        override fun createFromParcel(parcel: Parcel): Followee {
            return Followee(parcel)
        }

        override fun newArray(size: Int): Array<Followee?> {
            return arrayOfNulls(size)
        }
    }
}