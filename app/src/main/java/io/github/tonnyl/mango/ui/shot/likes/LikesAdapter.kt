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

package io.github.tonnyl.mango.ui.shot.likes

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.item_user.view.*


/**
 * Created by lizhaotailang on 2017/7/3.
 */

class LikesAdapter(context: Context, list: List<Like>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LikeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_like, parent, false), mListener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        val like = mList[position]
        with(viewHolder as LikeViewHolder) {
            GlideLoader.loadAvatar(itemView.avatar, like.user.avatarUrl)
            itemView.name.text = like.user.name
            itemView.user_name.text = like.user.username
        }
    }

    override fun getItemCount() = mList.size

    fun setOnItemClickListener(listener: ((View, Int) -> Unit)?) {
        mListener = listener
    }

    inner class LikeViewHolder(itemView: View, listener: ((View, Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val mListener = listener

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (mListener != null && view != null) {
                mListener.invoke(view, layoutPosition)
            }
        }

    }

}