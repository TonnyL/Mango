package io.github.tonnyl.mango.shots.page

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.util.FrescoLoader

import kotlinx.android.synthetic.main.item_shot.view.*

/**
 * Created by lizhaotailang on 2017/6/29.
 */

class ShotsAdapter(context: Context, list: MutableList<Shot>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mList = list
    private var mListener: ((View, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ShotViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_shot, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as ShotViewHolder
        val shot = mList[position]
        FrescoLoader.loadNormal(mContext, viewHolder.mDraweeView, shot.images.normal, shot.images.teaser)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemClickListener(listener: ((view: View, position: Int) -> Unit)?) {
        mListener = listener
    }

    fun updateData(list: MutableList<Shot>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    class ShotViewHolder(itemView: View, listener: ((view : View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val mListener = listener
        val mDraweeView: SimpleDraweeView

        init {
            itemView.setOnClickListener(this)
            mDraweeView = itemView.simple_drawee_view
        }

        override fun onClick(p0: View?) {
            if (p0 != null && mListener != null) {
                mListener.invoke(p0, layoutPosition)
            }
        }

    }

}