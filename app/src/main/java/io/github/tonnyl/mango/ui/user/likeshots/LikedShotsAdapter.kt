package io.github.tonnyl.mango.ui.user.likeshots

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.LikedShot
import io.github.tonnyl.mango.glide.GlideLoader
import kotlinx.android.synthetic.main.item_simple_list_shots.view.*

/**
 * Created by lizhaotailang on 2017/7/20.
 */

class LikedShotsAdapter(context: Context, list: List<LikedShot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContext = context
    private val mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShotLikeViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_simple_list_shots, parent, false), mListener)
    }

    override fun onBindViewHolder(holderFollower: RecyclerView.ViewHolder?, position: Int) {
        val shotLike = mList[position]
        with(holderFollower as ShotLikeViewHolder) {
            itemView.tag_gif.visibility = if (shotLike.shot.images.normal.endsWith(".gif")) View.VISIBLE else View.GONE
            GlideLoader.loadNormal(mContext, itemView.image_view, shotLike.shot.images.best())
        }
    }

    override fun getItemCount() = mList.size

    fun setItemClickListener(listener: ((view: View, position: Int) -> Unit)?) {
        mListener = listener
    }

    inner class ShotLikeViewHolder(itemView: View, listener: ((view: View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val mListener = listener

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