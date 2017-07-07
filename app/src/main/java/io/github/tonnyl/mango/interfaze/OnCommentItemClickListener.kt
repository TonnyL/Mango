package io.github.tonnyl.mango.interfaze

import android.view.View

/**
 * Created by lizhaotailang on 2017/7/5.
 */

interface OnCommentItemClickListener {

    fun OnCommentBodyClick(view: View, position: Int)

    fun OnAvatarClick(view: View, position: Int)

    fun OnLikeClick(view: View, position: Int)

}