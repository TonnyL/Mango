package io.github.tonnyl.mango.user.followers

import android.content.Context
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.item_user.view.*

/**
 * Created by lizhaotailang on 2017/7/29.
 */

class FollowersAdapter(context: Context, list: MutableList<User>) : RecyclerView.Adapter<FollowersAdapter.FollowerViewHolder>() {

    private val mContext = context
    private val mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false), mListener)
    }

    override fun onBindViewHolder(holderFollower: FollowerViewHolder?, position: Int) {
        val follower = mList[position]
        with(holderFollower as FollowerViewHolder) {
            GlideLoader.loadAvatar(mContext, itemView.avatar_drawee, follower.avatarUrl)

            itemView.name.text = follower.name
            itemView.user_name.text = follower.username

            itemView.bio.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(follower.bio, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(follower.bio)
            }
        }
    }

    override fun getItemCount() = mList.size

    class FollowerViewHolder(itemView: View, listener: ((view: View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val mListener = listener

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            if (view != null && mListener != null) {
                mListener.invoke(view, layoutPosition)
            }
        }
    }
}