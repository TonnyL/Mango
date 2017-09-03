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