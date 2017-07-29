package io.github.tonnyl.mango.shot.comments

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
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.user.UserProfileActivity
import io.github.tonnyl.mango.util.AccountManager
import kotlinx.android.synthetic.main.fragment_comments.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/8.
 */

class CommentsFragment : Fragment(), CommentsContract.View {

    private lateinit var mPresenter: CommentsContract.Presenter
    private var mCommentsAdapter: CommentsAdapter? = null
    private var mListSize = 0
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
            mCommentsAdapter?.clearData()
            mPresenter.fetchComments()
            mIsLoading = true
        }

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                if (dy > 0 && layoutManager.findLastCompletelyVisibleItemPosition() == mListSize - 1 && !mIsLoading) {
                    mPresenter.fetchComments()
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

    override fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(refresh_layout, message, Snackbar.LENGTH_SHORT).show()
        } ?: run {
            Snackbar.make(refresh_layout, R.string.something_wrong, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun cancelSendingIndicator(clearText: Boolean) {
        button_send.isEnabled = true
        button_send.visibility = View.VISIBLE
        progress_send.visibility = View.GONE
        if (clearText) {
            comment_edit.setText("")
        }
    }

    override fun showComments(comments: MutableList<Comment>) {
        mCommentsAdapter?.updateData(comments) ?: run {
            mCommentsAdapter = CommentsAdapter(context, comments)
            mCommentsAdapter?.setOnAvatarClickListener { _, position ->
                context.startActivity<UserProfileActivity>(UserProfileActivity.EXTRA_USER to comments[position].user)
            }
            recycler_view.adapter = mCommentsAdapter
        }

        mListSize += comments.size
        mIsLoading = false

        empty_view.visibility = if (comments.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun updateTitle(commentsCount: Int) {
        (activity as CommentsActivity).title = getString(R.string.comments).format(commentsCount)
    }

    private fun initViews() {
        recycler_view.layoutManager = LinearLayoutManager(context)

        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))

        AccountManager.authenticatedUser?.let {
            if (it.canUploadShot) {
                GlideLoader.loadAvatar(context, avatar_drawee, it.avatarUrl)
            } else {
                layout_add_comment.visibility = View.GONE
            }
        }
    }

}