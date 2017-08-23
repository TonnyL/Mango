package io.github.tonnyl.mango.util

/**
 * Created by lizhaotailang on 2017/6/27.
 *
 * Some constant strings but not included in [io.github.tonnyl.mango.retrofit.ApiConstants].
 */

class Constants {

    companion object {

        // If the user has logged in.
        @JvmField
        val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"

        // The access token string.
        @JvmField
        val ACCESS_TOKEN = "ACCESS_TOKEN"

        // The intent actions of app shortcuts
        @JvmField
        val INTENT_ACTION_POPULAR = "INTENT_ACTION_POPULAR"
        @JvmField
        val INTENT_ACTION_FOLLOWING = "INTENT_ACTION_FOLLOWING"
        @JvmField
        val INTENT_ACTION_RECENT = "INTENT_ACTION_RECENT"
        @JvmField
        val INTENT_ACTION_DEBUTS = "INTENT_ACTION_DEBUTS"

        // The ids of app shortcuts
        @JvmField
        val SHORTCUT_ID_FOLLOWING = "SHORTCUT_ID_FOLLOWING"
        @JvmField
        val SHORTCUT_ID_POPULAR = "SHORTCUT_ID_POPULAR"
        @JvmField
        val SHORTCUT_ID_RECENT = "SHORTCUT_ID_RECENT"
        @JvmField
        val SHORTCUT_ID_DEBUTS = "SHORTCUT_ID_DEBUTS"

    }

}