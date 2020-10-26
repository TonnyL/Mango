/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
import io.github.tonnyl.mango.databinding.FragmentCommentsBinding
import io.github.tonnyl.mango.glide.GlideLoader
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

    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!

    companion object {

        @JvmStatic
        fun newInstance(): CommentsFragment {
            return CommentsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = FragmentCommentsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        mPresenter.subscribe()

        binding.refreshLayout.setOnRefreshListener {
            mPresenter.loadComments()
            mIsLoading = true
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView?.layoutManager as LinearLayoutManager
                if ((dy > 0) && (layoutManager.findLastVisibleItemPosition() == binding.recyclerView.adapter.itemCount - 1)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return true
    }

    override fun setPresenter(presenter: CommentsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        binding.refreshLayout.post({
            binding.refreshLayout.isRefreshing = loading
        })
    }

    override fun cancelSendingIndicator(clearText: Boolean) {
        binding.buttonSend.isEnabled = true
        binding.buttonSend.visibility = View.VISIBLE
        binding.progressSend.visibility = View.GONE
        if (clearText) {
            binding.commentEdit.setText("")
        }
    }

    override fun showComments(comments: List<Comment>) {
        if (mCommentsAdapter == null) {
            context?.let {
                mCommentsAdapter = CommentsAdapter(it, comments)

                mCommentsAdapter?.setOnAvatarClickListener { _, position ->
                    it.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to comments[position].user)
                }
                binding.recyclerView.adapter = mCommentsAdapter
            }
        }

        mIsLoading = false
    }

    override fun updateTitle(commentsCount: Int) {
        (activity as CommentsActivity).title = getString(R.string.comments_formatted).format(commentsCount)
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        binding.emptyView.visibility = if (visible && (mCommentsAdapter == null)) View.VISIBLE else View.GONE
    }

    override fun setEditorVisible(visible: Boolean, avatarUrl: String) {
        binding.layoutAddComment.visibility = if (visible) View.VISIBLE else View.GONE
        GlideLoader.loadAvatar(binding.avatar, avatarUrl)
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.refreshLayout.setColorSchemeColors(ContextCompat.getColor(context ?: return, R.color.colorAccent))
    }

    override fun showNetworkError() {
        Snackbar.make(binding.refreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
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
        Snackbar.make(binding.layoutAddComment, R.string.add_comment_failed, Snackbar.LENGTH_SHORT).show()
    }

}