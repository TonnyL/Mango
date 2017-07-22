package io.github.tonnyl.mango.main.shots

import android.view.View

/**
 * Created by lizhaotailang on 2017/7/20.
 */

interface OnRecyclerViewItemClickListener {

    fun onItemClick(view: View, position: Int)

    fun onAvatarClick(view: View, position: Int)

}