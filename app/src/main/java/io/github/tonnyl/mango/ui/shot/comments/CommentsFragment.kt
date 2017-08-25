package io.github.tonnyl.mango.ui.shot.comments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.extension.loadAvatar
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import kotlinx.android.synthetic.main.fragment_comments.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/8.
 *
 * Main ui for the comments of a shot screen.
 */

class CommentsFragment : Fragment(), CommentsContract.View {

    private lateinit var mPresenter: CommentsContract.Presenter
    private var mCommentsAdapter: CommentsAdapter? = null
    private var mIsLoading = false

    companion object {

        @JvmStatic
        fun newInstance(): CommentsFragment {
            return CommentsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        mPresenter.subscribe()

        refresh_layout.setOnRefreshListener {
            mPresenter.loadComments()
            mIsLoading = true
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                if ((dy > 0) && (layoutManager.findLastVisibleItemPosition() == recycler_view.adapter.itemCount - 1)
                        && !mIsLoading) {
                    mPresenter.loadMoreComments()
                    mIsLoading = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity.onBackPressed()
        }
        return true
    }

    override fun setPresenter(presenter: CommentsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.post({
            refresh_layout.isRefreshing = loading
        })
    }

    override fun cancelSendingIndicator(clearText: Boolean) {
        button_send.isEnabled = true
        button_send.visibility = View.VISIBLE
        progress_send.visibility = View.GONE
        if (clearText) {
            comment_edit.setText("")
        }
    }

    override fun showComments(comments: List<Comment>) {
        if (mCommentsAdapter == null) {
            mCommentsAdapter = CommentsAdapter(context, comments)
            mCommentsAdapter?.setOnAvatarClickListener { _, position ->
                context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to comments[position].user)
            }
            recycler_view.adapter = mCommentsAdapter
        }

        mIsLoading = false
    }

    override fun updateTitle(commentsCount: Int) {
        (activity as CommentsActivity).title = getString(R.string.comments_formatted).format(commentsCount)
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        empty_view.visibility = if (visible && (mCommentsAdapter == null)) View.VISIBLE else View.GONE
    }

    override fun setEditorVisible(visible: Boolean, avatarUrl: String) {
        layout_add_comment.visibility = if (visible) View.VISIBLE else View.GONE
        avatar.loadAvatar(avatarUrl)
    }

    private fun initViews() {
        recycler_view.layoutManager = LinearLayoutManager(context)
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    }

    override fun showNetworkError() {
        Snackbar.make(refresh_layout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun notifyDataAllRemoved(size: Int) {
        mCommentsAdapter?.notifyItemRangeRemoved(0, size)
        mIsLoading = false
    }

    override fun notifyDataAdded(startPosition: Int, size: Int) {
        mCommentsAdapter?.notifyItemRangeInserted(startPosition, size)
        mIsLoading = false
    }

    override fun showCreateCommentFailed() {
        Snackbar.make(layout_add_comment, R.string.add_comment_failed, Snackbar.LENGTH_SHORT).show()
    }

}