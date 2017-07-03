package io.github.tonnyl.mango.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by lizhaotailang on 2017/6/30.
 *
 * {
 * "hidpi" : null,
 * "normal" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch.png",
 * "teaser" : "https://d13yacurqjgara.cloudfront.net/users/1/screenshots/471756/sasquatch_teaser.png"
 * }
 */

class Images {

    @SerializedName("hidpi")
    @Expose
    var hidpi: String = ""

    @SerializedName("normal")
    @Expose
    var normal: String = ""

    @SerializedName("teaser")
    @Expose
    var teaser: String = ""

}