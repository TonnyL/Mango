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

package io.github.tonnyl.mango.ui.shot.likes

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
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.databinding.FragmentSimpleListBinding
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import kotlinx.android.synthetic.main.fragment_simple_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/8.
 *
 * Main ui for the likes of a shot screen.
 */

class LikesFragment : Fragment(), LikesContract.View {

    private lateinit var mPresenter: LikesContract.Presenter

    private var mLayoutManager: LinearLayoutManager? = null
    private var mAdapter: LikesAdapter? = null
    private var mIsLoading = true

    private var _binding: FragmentSimpleListBinding? = null
    private val binding get() = _binding!!

    companion object {
        @JvmStatic
        fun newInstance(): LikesFragment {
            return LikesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding = FragmentSimpleListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        mPresenter.subscribe()

        binding.refreshLayout.setOnRefreshListener {
            mIsLoading = true
            mPresenter.loadLikes()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && (mLayoutManager?.findLastVisibleItemPosition() == binding.recyclerView.adapter.itemCount - 1) && !mIsLoading) {
                    mPresenter.loadMoreLikes()
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

    override fun setPresenter(presenter: LikesContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        binding.refreshLayout.post({
            binding.refreshLayout.isRefreshing = loading
        })
    }

    override fun setEmptyViewVisibility(visible: Boolean) {
        binding.emptyView.visibility = if (visible && (mAdapter == null)) View.VISIBLE else View.GONE
    }

    override fun showNetworkError() {
        Snackbar.make(binding.refreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLikes(likes: List<Like>) {
        if (mAdapter == null) {
            mAdapter = LikesAdapter(context ?: return, likes)
            mAdapter?.setOnItemClickListener { _, position ->
                context?.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to likes[position].user)
            }
            binding.recyclerView.adapter = mAdapter
        }

        mIsLoading = false
    }

    override fun updateTitle(likeCount: Int) {
        (activity as LikesActivity).title = getString(R.string.likes_formatted).format(likeCount)
    }

    override fun notifyDataAllRemoved(size: Int) {
        mAdapter?.notifyItemRangeRemoved(0, size)
        mIsLoading = false
    }

    override fun notifyDataAdded(startPosition: Int, size: Int) {
        mAdapter?.notifyItemRangeInserted(startPosition, size)
        mIsLoading = false
    }

    private fun initViews() {
        mLayoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = mLayoutManager
        binding.refreshLayout.setColorSchemeColors(ContextCompat.getColor(context ?: return, R.color.colorAccent))
    }

}