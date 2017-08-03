package io.github.tonnyl.mango.shot.likes

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
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

class LikesAdapter(context: Context, list: MutableList<Like>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    private var mClearData = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return LikeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false), mListener)
    }

    override fun onBindViewHolder(holderFollower: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holderFollower as LikeViewHolder
        val like = mList[position]
        GlideLoader.loadAvatar(mContext, viewHolder.itemView.avatar_drawee, like.user.avatarUrl)

        viewHolder.itemView.name.text = like.user.name
        viewHolder.itemView.user_name.text = like.user.username

        viewHolder.itemView.bio.text = if (Build.VERSION.SDK_INT >= 24) {
            Html.fromHtml(like.user.bio, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(like.user.bio)
        }
    }

    override fun getItemCount() = mList.size

    fun setOnAvatarClickListener(listener: ((View, Int) -> Unit)?) {
        mListener = listener
    }

    fun updateData(list: MutableList<Like>) {
        if (mClearData) {
            mList.clear()
            notifyItemRangeRemoved(0, mList.size)
            mClearData = false
        }
        mList.addAll(list)
        notifyItemRangeInserted(mList.size - list.size, list.size)
    }

    fun clearData() {
        mClearData = true
    }

    inner class LikeViewHolder(itemView: View, listener: ((View, Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mListener = listener

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