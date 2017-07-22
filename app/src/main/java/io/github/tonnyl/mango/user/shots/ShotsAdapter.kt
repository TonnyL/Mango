package io.github.tonnyl.mango.user.shots

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.glide.GlideLoader

import kotlinx.android.synthetic.main.item_simple_list_shots.view.*

/**
 * Created by lizhaotailang on 2017/7/20.
 */
class ShotsAdapter(context: Context, list: MutableList<Shot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    private var mClearData = false

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_simple_list_shots, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as ShotViewHolder
        val shot = mList[position]
        viewHolder.itemView.tag_gif.visibility = if (shot.images.normal.endsWith(".gif")) View.VISIBLE else View.GONE
        GlideLoader.loadNormal(mContext, viewHolder.itemView.image_view, shot.images.best())
    }

    override fun getItemCount() = mList.size

    fun setItemClickListener(listener: ((view: View, position: Int) -> Unit)?) {
        mListener = listener
    }

    fun updateData(list: MutableList<Shot>) {
        if (mClearData) {
            mList.clear()
            notifyItemRangeRemoved(0, mList.size)
            mClearData = false
        }
        mList.addAll(list)
        notifyItemRangeInserted(mList.size - list.size, list.size)
    }

    inner class ShotViewHolder(itemView: View, listener: ((view: View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

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