package io.github.tonnyl.mango.user.follower

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.data.User

/**
 * Created by lizhaotailang on 2017/7/29.
 */
class FollowerAdapter(context: Context, list: MutableList<User>) : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FollowerViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holderFollower: FollowerViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class FollowerViewHolder(itemView: View, listener: ((view: View, position: Int) -> Unit)?) : RecyclerView.ViewHolder(itemView) {


    }
}