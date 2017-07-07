package io.github.tonnyl.mango.list

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.interfaze.OnCommentItemClickListener
import io.github.tonnyl.mango.util.FrescoLoader
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by lizhaotailang on 2017/7/3.
 */

class CommentsAdapter(context: Context, list: MutableList<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: OnCommentItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as CommentViewHolder
        val comment = mList[position]
        FrescoLoader.loadAvatar(viewHolder.itemView.avatar_drawee, comment.user.avatar_url)

        if (Build.VERSION.SDK_INT >= 24) {
            viewHolder.itemView.comment_body.text = Html.fromHtml(comment.body, Html.FROM_HTML_MODE_LEGACY)
        } else {
            viewHolder.itemView.comment_body.text = Html.fromHtml(comment.body)
        }

        viewHolder.itemView.user_name.text = comment.user.username
        viewHolder.itemView.created_time.text = comment.createdAt.replace("T", " ").replace("Z", " ")

        viewHolder.itemView.comment_likes_count.text = comment.likesCount.toString()

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemClickListener(listener: OnCommentItemClickListener?) {
        mListener = listener
    }

    fun updateData(list: MutableList<Comment>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    class CommentViewHolder(itemView: View, listener: OnCommentItemClickListener?) : RecyclerView.ViewHolder(itemView) {

        val mListener = listener

        init {
            itemView.avatar_drawee.setOnClickListener({ view ->
                if (mListener != null && view != null) {
                    mListener.OnAvatarClick(view, layoutPosition)
                }
            })

            itemView.layout_comment_body.setOnClickListener({ view ->
                if (mListener != null && view != null) {
                    mListener.OnCommentBodyClick(view, layoutPosition)
                }
            })

            itemView.comment_like.setOnClickListener({ view ->
                if (mListener != null && view != null) {
                    mListener.OnLikeClick(view, layoutPosition)
                }
            })
        }

    }

}