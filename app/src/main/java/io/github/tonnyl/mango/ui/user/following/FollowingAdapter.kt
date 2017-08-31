package io.github.tonnyl.mango.ui.user.following

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Followee
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by lizhaotailang on 2017/7/29.
 */
class FollowingAdapter(context: Context, list: List<Followee>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FollowingViewHolder {
        return FollowingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val followee = mList[position]
        with(holder as FollowingViewHolder) {
            GlideLoader.loadAvatar(itemView.avatar, followee.followee.avatarUrl)
            itemView.name.text = followee.followee.name
            itemView.user_name.text = followee.followee.username
            itemView.bio.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(followee.followee.bio, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(followee.followee.bio)
            }
        }
    }

    override fun getItemCount() = mList.size

    fun setOnItemClickListener(listener: ((View, Int) -> Unit)?) {
        mListener = listener
    }

    class FollowingViewHolder(itemView: View, listener: ((view: View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

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