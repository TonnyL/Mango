package io.github.tonnyl.mango.main.shots

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

class ShotsAdapter(context: Context, list: MutableList<Shot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: OnRecyclerViewItemClickListener? = null

    private var mClearData = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shot, parent, false), mListener)
    }

    override fun onBindViewHolder(holderFollower: RecyclerView.ViewHolder?, position: Int) {
        val shot = mList[position]
        with(holderFollower as ShotViewHolder) {
            GlideLoader.loadAvatar(mContext, itemView.avatar, shot.user?.avatarUrl)
            GlideLoader.loadNormal(mContext, itemView.shot_image_view, shot.images.best())
            itemView.tag_gif.visibility = if (shot.images.normal.endsWith(".gif")) View.VISIBLE else View.GONE
            itemView.shot_title.text = mContext.getString(R.string.shot_title).format(shot.user?.name, shot.title)
        }
    }

    override fun getItemCount() = mList.size

    fun setItemClickListener(listener: OnRecyclerViewItemClickListener) {
        mListener = listener
    }

    fun updateData(list: MutableList<Shot>) {
        if (mClearData) {
            mList.clear()
            notifyItemRangeRemoved(0, mList.size)
            mClearData = false
        }
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearData() {
        mClearData = true
    }

    inner class ShotViewHolder(itemView: View, listener: OnRecyclerViewItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        val mListener = listener

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