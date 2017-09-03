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

package io.github.tonnyl.mango.ui.main.shots

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.glide.GlideLoader

import kotlinx.android.synthetic.main.item_shot.view.*

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class ShotsAdapter(context: Context, list: List<Shot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: OnRecyclerViewItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shot, parent, false), mListener)
    }

    override fun onBindViewHolder(holderFollower: RecyclerView.ViewHolder?, position: Int) {
        if (position <= mList.size) {
            val shot = mList[position]
            with(holderFollower as ShotViewHolder) {
                GlideLoader.loadAvatar(itemView.avatar, shot.user?.avatarUrl)
                GlideLoader.loadNormal(itemView.shot_image_view, shot.images.best())
                itemView.tag_gif.visibility = if (shot.animated) View.VISIBLE else View.GONE
                itemView.shot_title.text = mContext.getString(R.string.shot_title).format(shot.user?.name, shot.title)
            }
        }
    }

    override fun getItemCount() = mList.size

    fun setItemClickListener(listener: OnRecyclerViewItemClickListener) {
        mListener = listener
    }

    inner class ShotViewHolder(itemView: View, listener: OnRecyclerViewItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        private val mListener = listener

        init {
            itemView.avatar.setOnClickListener({ view ->
                mListener?.onAvatarClick(view, layoutPosition)
            })

            itemView.setOnClickListener({ view ->
                mListener?.onItemClick(view, layoutPosition)
            })
        }

    }

}