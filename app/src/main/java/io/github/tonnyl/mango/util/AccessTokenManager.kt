package io.github.tonnyl.mango.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import io.github.tonnyl.mango.data.AccessToken

/**
 * Created by lizhaotailang on 2017/6/30.
 */
object AccessTokenManager {

    var accessToken: AccessToken? = null

    fun init(context: Context) {
        val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        accessToken = Gson().fromJson(sp.getString(Constants.ACCESS_TOKEN, ""), AccessToken::class.java)
    }

    fun clear() {
        accessToken = null
    }

}