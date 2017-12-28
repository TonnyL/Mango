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
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by lizhaotailang on 2017/6/26.
 *
 * {
 * "access_token" : "29ed478ab86c07f1c069b1af76088f7431396b7c4a2523d06911345da82224a0",
 * "token_type" : "bearer",
 * "scope" : "public write"
 * }
 */

@Parcelize
@SuppressLint("ParcelCreator")
@Entity(tableName = "access_token")
data class AccessToken(
        @ColumnInfo(name = "access_token")
        @SerializedName("access_token")
        @Expose
        val accessToken: String,

        @ColumnInfo(name = "token_type")
        @SerializedName("token_type")
        @Expose
        val tokenType: String,

        @ColumnInfo(name = "scope")
        @SerializedName("scope")
        @Expose
        val scope: String,

        // User id
        @PrimaryKey
        @ColumnInfo(name = "id")
        @Expose
        var id: Long
) : Parcelable