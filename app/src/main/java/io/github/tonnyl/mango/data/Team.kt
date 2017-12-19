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

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
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

@Parcelize
@SuppressLint("ParcelCreator")
data class Team(
        @SerializedName("id")
        @Expose
        val id: Long,

        @SerializedName("name")
        @Expose
        val name: String,

        @SerializedName("username")
        @Expose
        val username: String,

        @SerializedName("html_url")
        @Expose
        val htmlUrl: String,

        @SerializedName("avatar_url")
        @Expose
        val avatarUrl: String,

        @SerializedName("bio")
        @Expose
        val bio: String,

        @SerializedName("location")
        @Expose
        val location: String?,

        @SerializedName("links")
        @Expose
        val links: Links,

        @SerializedName("buckets_count")
        @Expose
        val bucketsCount: Int,

        @SerializedName("comments_received_count")
        @Expose
        val commentsReceivedCount: Int,

        @SerializedName("followers_count")
        @Expose
        val followersCount: Int,

        @SerializedName("followings_count")
        @Expose
        val followingsCount: Int,

        @SerializedName("likes_count")
        @Expose
        val likesCount: Int,

        @SerializedName("likes_received_count")
        @Expose
        val likesReceivedCount: Int,

        @SerializedName("members_count")
        @Expose
        val membersCount: Int,

        @SerializedName("projects_count")
        @Expose
        val projectsCount: Int,

        @SerializedName("rebounds_received_count")
        @Expose
        val reboundsReceivedCount: Int,

        @SerializedName("shots_count")
        @Expose
        val shotsCount: Int,

        @SerializedName("can_upload_shot")
        @Expose
        val canUploadShot: Boolean,

        @SerializedName("type")
        @Expose
        val type: String,

        @SerializedName("pro")
        @Expose
        val pro: Boolean,

        @SerializedName("buckets_url")
        @Expose
        val bucketsUrl: String,

        @SerializedName("followers_url")
        @Expose
        val followersUrl: String,

        @SerializedName("following_url")
        @Expose
        val followingUrl: String,

        @SerializedName("likes_url")
        @Expose
        val likesUrl: String,

        @SerializedName("members_url")
        @Expose
        val membersUrl: String,

        @SerializedName("shots_url")
        @Expose
        val shotsUrl: String,

        @SerializedName("team_shots_url")
        @Expose
        val teamShotsUrl: String,

        @SerializedName("created_at")
        @Expose
        val createdAt: Date,

        @SerializedName("updated_at")
        @Expose
        val updatedAt: Date
) : Parcelable