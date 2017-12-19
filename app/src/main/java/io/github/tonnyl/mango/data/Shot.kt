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
 * Created by lizhaotailang on 2017/6/29.
 *
 * {
 * "id" : 471756,
 * "title" : "Sasquatch",
 * "description" : "<p>Quick, messy, five minute sketch of something that might become a fictional something.</p>",
 * "width" : 400,
 * "height" : 300,
 * "images" : {
 * "hidpi" : null,
 * "normal" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png",
 * "teaser" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png"
 * },
 * "views_count" : 4372,
 * "likes_count" : 149,
 * "comments_count" : 27,
 * "attachments_count" : 0,
 * "rebounds_count" : 2,
 * "buckets_count" : 8,
 * "created_at" : "2012-03-15T01:52:33Z",
 * "updated_at" : "2012-03-15T02:12:57Z",
 * "html_url" : "https://dribbble.com/shots/471756-Sasquatch",
 * "attachments_url" : "https://api.dribbble.com/v1/shots/471756/attachments",
 * "buckets_url" : "https://api.dribbble.com/v1/shots/471756/buckets",
 * "comments_url" : "https://api.dribbble.com/v1/shots/471756/comments",
 * "likes_url" : "https://api.dribbble.com/v1/shots/471756/likes",
 * "projects_url" : "https://api.dribbble.com/v1/shots/471756/projects",
 * "rebounds_url" : "https://api.dribbble.com/v1/shots/471756/rebounds",
 * "animated" : false,
 * "tags" : [
 * "fiction",
 * "sasquatch",
 * "sketch",
 * "wip"
 * ],
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
 * },
 * "team" : {
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
 * }
 */

@Parcelize
@SuppressLint("ParcelCreator")
data class Shot(
        @SerializedName("id")
        val id: Long,

        @SerializedName("title")
        @Expose
        val title: String,

        @SerializedName("description")
        @Expose
        val description: String?,

        @SerializedName("width")
        @Expose
        val width: Int,

        @SerializedName("height")
        @Expose
        val height: Int,

        @SerializedName("images")
        @Expose
        val images: Images,

        @SerializedName("views_count")
        @Expose
        val viewsCount: Int,

        @SerializedName("likes_count")
        @Expose
        var likesCount: Int,

        @SerializedName("comments_count")
        @Expose
        var commentsCount: Int,

        @SerializedName("attachments_count")
        @Expose
        val attachmentsCount: Int,

        @SerializedName("rebounds_count")
        @Expose
        val reboundsCount: Int,

        @SerializedName("buckets_count")
        @Expose
        val bucketsCount: Int,

        @SerializedName("created_at")
        @Expose
        val createdAt: Date,

        @SerializedName("updated_at")
        @Expose
        val updatedAt: Date,

        @SerializedName("html_url")
        @Expose
        val htmlUrl: String,

        @SerializedName("attachments_url")
        @Expose
        val attachmentsUrl: String,

        @SerializedName("buckets_url")
        @Expose
        val bucketsUrl: String,

        @SerializedName("comments_url")
        @Expose
        val commentsUrl: String,

        @SerializedName("likes_url")
        @Expose
        val likesUrl: String,

        @SerializedName("projects_url")
        @Expose
        val projectsUrl: String,

        @SerializedName("rebounds_url")
        @Expose
        val reboundsUrl: String,

        @SerializedName("animated")
        @Expose
        val animated: Boolean,

        @SerializedName("tags")
        @Expose
        val tags: List<String>,

        @SerializedName("user")
        @Expose
        var user: User?,

        @SerializedName("team")
        @Expose
        val team: Team?
) : Parcelable