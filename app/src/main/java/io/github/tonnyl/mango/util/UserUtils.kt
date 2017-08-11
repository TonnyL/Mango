package io.github.tonnyl.mango.util

import io.github.tonnyl.mango.data.User

/**
 * Created by lizhaotailang on 2017/8/6.
 *
 * A collection of some usable functions about [User]
 */
class UserUtils {

    companion object {

        // Find out if the user have permission to creating a comment.
        // Only a player or team can be granted.
        @JvmStatic
        fun canUserComment(user: User?): Boolean {
            return (user != null) && ("Player" == user.type || "Team" == user.type)
        }
    }

}