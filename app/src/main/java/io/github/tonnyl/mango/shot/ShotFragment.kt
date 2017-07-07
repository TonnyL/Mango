package io.github.tonnyl.mango.shot

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.*
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.util.FrescoLoader
import kotlinx.android.synthetic.main.fragment_shot.*
import android.view.ViewGroup
import android.widget.TextView
import io.github.tonnyl.mango.list.ListActivity
import io.github.tonnyl.mango.user.UserActivity

import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class ShotFragment : Fragment(), ShotContract.View {

    private lateinit var mPresenter: ShotContract.Presenter

    companion object {

        val KEY_SHOT_ID = "KEY_SHOT_ID"

        fun newInstance(): ShotFragment {
            return ShotFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setShotId(activity.intent.getLongExtra(KEY_SHOT_ID, 0L))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_shot, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        val act = activity as ShotActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        act.supportActionBar?.setDisplayShowTitleEnabled(false)

        fab.setOnClickListener({
            mPresenter.toggleLike()
        })

        avatar.setOnClickListener({
            mPresenter.navigateToUserProfile()
        })

        layout_likes.setOnClickListener({
            mPresenter.navigateToLikes()
        })

        layout_buckets.setOnClickListener({
            mPresenter.navigateToBuckets()
        })

        layout_comments.setOnClickListener({
            mPresenter.navigateToComments()
        })

        layout_attachments.setOnClickListener({
            mPresenter.navigateToAttachments()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.shot, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) {
            activity.onBackPressed()
        }
        return true
    }

    override fun setPresenter(presenter: ShotContract.Presenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isResumed && isAdded
    }

    override fun showMessage(message: String?) {
        if (message != null) {
            Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(fab, getString(R.string.something_wrong), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun show(shot: Shot) {
        // Show the shot image
        FrescoLoader.loadNormalWithPalette(context, simple_drawee_view, shot.images.normal, shot.images.teaser, object : FrescoLoader.OnPaletteProcessCallback {

            override fun OnPaletteAvailable(palette: Palette?) {

            }

            override fun OnPaletteNotAvailable() {

            }
        })

        FrescoLoader.loadAvatar(avatar, shot.user!!.avatar_url)

        shot_title.text = shot.title

        if (shot.description != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                shot_description.text = Html.fromHtml(shot.description, Html.FROM_HTML_MODE_LEGACY)
            } else {
                shot_description.text = Html.fromHtml(shot.description)
            }
            shot_description.movementMethod = LinkMovementMethod.getInstance()
        } else {
            shot_description.visibility = View.GONE
        }

        user_name.text = shot.user?.username
        created_time.text = shot.createdAt.replace("T", " ").replace("Z", "")

        likes_count.text = shot.likesCount.toString()
        views_count.text = shot.viewsCount.toString()
        buckets_count.text = shot.bucketsCount.toString()
        comments_count.text = shot.commentsCount.toString()
        attachments_count.text = shot.attachmentsCount.toString()

        // Show the tags
        showTags(shot.tags)
    }

    override fun setLikeStatus(like: Boolean) {
        fab.setImageResource(if (like) {
            R.drawable.ic_favorite_white_24dp
        } else {
            R.drawable.ic_favorite_border_white_24dp
        })
    }

    override fun updateLikeCount(count: Int) {
        likes_count.text = count.toString()
    }

    override fun navigateToUserProfile(userId: Long) {
        context.startActivity<UserActivity>(UserActivity.EXTRA_USER_ID to userId)
    }

    override fun navigateToComments(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_COMMENTS, ListActivity.EXTRA_ID to shotId)
    }

    override fun navigateToBuckets(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_BUCKETS, ListActivity.EXTRA_ID to shotId)
    }

    override fun navigateToLikes(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_LIKES, ListActivity.EXTRA_ID to shotId)
    }

    override fun navigateToAttachments(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_ATTACHMENTS, ListActivity.EXTRA_ID to shotId)
    }

    private fun showTags(tags: List<String>) {
        for (tag in tags) {
            val textView = TextView(context)
            textView.background = ContextCompat.getDrawable(context, R.drawable.tag_background)
            textView.text = tag
            textView.gravity = Gravity.CENTER
            textView.setPadding(16, 0, 16, 0)
        }

    }

}