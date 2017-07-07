package io.github.tonnyl.mango.list

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Attachment
import io.github.tonnyl.mango.data.Bucket
import io.github.tonnyl.mango.data.Comment
import io.github.tonnyl.mango.data.Like
import io.github.tonnyl.mango.interfaze.OnCommentItemClickListener
import io.github.tonnyl.mango.user.UserActivity
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/7/3.
 */

class ListFragment : Fragment(), ListContract.View {

    private lateinit var mPresenter: ListContract.Presenter
    private var mCommentsAdapter: CommentsAdapter? = null
    private var mType : Int? = null

    companion object {
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setId(activity.intent.getLongExtra(ListActivity.EXTRA_ID, 0L))
        mType = activity.intent.getIntExtra(ListActivity.EXTRA_TYPE, ListActivity.TYPE_COMMENTS)
        mPresenter.setType(mType!!)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        initViews()

        comment_edit.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    button_send.setColorFilter(ContextCompat.getColor(context, R.color.hint))
                    button_send.isEnabled = false
                } else {
                    button_send.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent))
                    button_send.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                button_send.isEnabled = (!p0.isNullOrBlank())
            }
        })

        button_send.setOnClickListener {
            button_send.visibility = View.GONE
            progress_send.visibility = View.VISIBLE
            mPresenter.createComment(comment_edit.text.toString())
        }

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

    override fun setPresenter(presenter: ListContract.Presenter) {
        mPresenter = presenter
    }

    override fun setLoadingIndicator(loading: Boolean) {
        refresh_layout.post({
            refresh_layout.isRefreshing = loading
        })
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Snackbar.make(refresh_layout, message, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(refresh_layout, R.string.something_wrong, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun showMessage(resId: Int) {
        this.showMessage(getString(resId))
    }

    override fun setUpViews(titleId: Int, showAddComment: Boolean) {
        toolbar.title = getString(titleId)
        if (showAddComment) {
            layout_add_comment.visibility = View.VISIBLE
        } else {
            layout_add_comment.visibility = View.GONE
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
        if (mCommentsAdapter == null) {
            mCommentsAdapter = CommentsAdapter(context, comments)
            mCommentsAdapter?.setItemClickListener(object : OnCommentItemClickListener {

                override fun OnCommentBodyClick(view: View, position: Int) {
                   Toast.makeText(context, comments[position].body, Toast.LENGTH_SHORT).show()
                }

                override fun OnAvatarClick(view: View, position: Int) {
                    context.startActivity<UserActivity>(UserActivity.EXTRA_USER_ID to comments[position].user.id)
                }

                override fun OnLikeClick(view: View, position: Int) {
                    Toast.makeText(context, "" + comments[position].likesCount, Toast.LENGTH_SHORT).show()
                }
            })
            recycler_view.adapter = mCommentsAdapter
        } else {
            mCommentsAdapter?.updateData(comments)
        }

        empty_view.visibility = if (comments.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun showLikes(likes: MutableList<Like>) {

    }

    override fun showBuckets(buckets: MutableList<Bucket>) {

    }

    override fun showAttachments(attachments: MutableList<Attachment>) {

    }

    private fun initViews() {
        val act = activity as ListActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler_view.layoutManager = LinearLayoutManager(context)
        refresh_layout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    }

}