package io.github.tonnyl.mango.ui.shot.comments

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.item_comment.view.*

/**
 * Created by lizhaotailang on 2017/7/3.
 */

class CommentsAdapter(context: Context, list: List<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false), mListener)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder?, position: Int) {
        val comment = mList[position]
        with(viewHolder as CommentViewHolder) {
            GlideLoader.loadAvatar(mContext, itemView.avatar, comment.user.avatarUrl)

            itemView.comment_body.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(comment.body, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(comment.body)
            }

            itemView.name.text = comment.user.name
            itemView.user_name.text = comment.user.username
            itemView.created_time.text = DateUtils.getRelativeTimeSpanString(comment.createdAt.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
        }
    }

    override fun getItemCount() = mList.size

    fun setOnAvatarClickListener(listener: ((View, Int) -> Unit)?) {
        mListener = listener
    }

    class CommentViewHolder(itemView: View, listener: ((View, Int) -> Unit)?) : RecyclerView.ViewHolder(itemView) {

        private val mListener = listener

        init {
            itemView.avatar.setOnClickListener({ view ->
                if (mListener != null && view != null) {
                    mListener.invoke(view, layoutPosition)
                }
            })
        }
    }

}