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

package io.github.tonnyl.mango.epoxy

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.glide.GlideLoader

/**
 * Created by lizhaotailang on 13/12/2017.
 */

@EpoxyModelClass(layout = R.layout.item_like)
abstract class LikeModel : EpoxyModelWithHolder<LikeModel.LikeHolder>() {

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    lateinit var itemOnClickListener: View.OnClickListener

    @EpoxyAttribute
    lateinit var like: Like

    override fun bind(holder: LikeHolder?) {
        super.bind(holder)

        with(holder ?: return) {
            itemLayout?.setOnClickListener(itemOnClickListener)
            GlideLoader.loadAvatar(avatarImageView ?: return, like.user.avatarUrl)
            nameTextView?.text = like.user.name
            userNameTextView?.text = like.user.username
        }
    }

    inner class LikeHolder : EpoxyHolder() {

        var itemLayout: View? = null
        var userNameTextView: TextView? = null
        var avatarImageView: ImageView? = null
        var nameTextView: TextView? = null

        override fun bindView(itemView: View?) {
            with(itemView ?: return) {
                itemLayout = findViewById(R.id.like_layout)
                userNameTextView = findViewById(R.id.user_name)
                nameTextView = findViewById(R.id.name)
                avatarImageView = findViewById(R.id.avatar)
            }
        }
    }

}