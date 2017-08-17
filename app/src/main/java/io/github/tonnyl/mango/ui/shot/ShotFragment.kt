package io.github.tonnyl.mango.ui.shot

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.text.Html
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.ui.shot.comments.CommentsActivity
import io.github.tonnyl.mango.ui.shot.comments.CommentsPresenter
import io.github.tonnyl.mango.ui.shot.likes.LikesActivity
import io.github.tonnyl.mango.ui.shot.likes.LikesPresenter
import io.github.tonnyl.mango.ui.user.UserProfileActivity
import io.github.tonnyl.mango.ui.user.UserProfilePresenter
import kotlinx.android.synthetic.main.fragment_shot.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.share
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Main ui for the shot screen.
 */

class ShotFragment : Fragment(), ShotContract.View {

    private lateinit var mPresenter: ShotContract.Presenter
    private val rgbs = arrayListOf<Int>()

    companion object {

        @JvmStatic
        fun newInstance(): ShotFragment {
            return ShotFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_shot, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        initView()

        fab.setOnClickListener({
            mPresenter.toggleLike()
        })

        avatar.setOnClickListener({
            mPresenter.navigateToUserProfile()
        })

        button_likes.setOnClickListener({
            mPresenter.navigateToLikes()
        })

        button_comments.setOnClickListener({
            mPresenter.navigateToComments()
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_shot, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            android.R.id.home -> activity.onBackPressed()
            R.id.action_open_in_browser -> mPresenter.openInBrowser()
            R.id.action_share -> mPresenter.share()
        }
        return true
    }

    override fun setPresenter(presenter: ShotContract.Presenter) {
        mPresenter = presenter
    }

    override fun showNetworkError() {
        Snackbar.make(fab, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun show(shot: Shot) {
        // Show the menu_shot image
        GlideLoader.loadHighQualityWithPalette(context, shot_image_view, shot.images.best(), object : GlideLoader.OnPaletteProcessCallback {
            override fun OnPaletteGenerated(palette: Palette?) {
                palette?.let {
                    showPalette(palette)
                } ?: run {
                    palette_layout.visibility = View.GONE
                }
            }

            override fun OnPaletteNotAvailable() {
                context.runOnUiThread {
                    palette_layout.visibility = View.GONE
                }
            }
        })

        shot.user?.let {
            GlideLoader.loadAvatar(context, avatar, it.avatarUrl)
        }

        toolbar.title = shot.title

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

        name.text = shot.user?.name
        user_name.text = shot.user?.username
        created_time.text = DateUtils.getRelativeTimeSpanString(shot.createdAt.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)

        button_likes.text = getString(R.string.likes_formatted).format(shot.likesCount)
        button_views.text = getString(R.string.views_formatted).format(shot.viewsCount)
        button_comments.text = getString(R.string.comments_formatted).format(shot.commentsCount)

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
        button_likes.text = getString(R.string.likes_formatted).format(count)
    }

    override fun navigateToUserProfile(user: User) {
        context.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to user)
    }

    override fun navigateToComments(shot: Shot) {
        context.startActivity<CommentsActivity>(CommentsPresenter.EXTRA_SHOT to shot)
    }

    override fun navigateToLikes(shot: Shot) {
        context.startActivity<LikesActivity>(LikesPresenter.EXTRA_SHOT to shot)
    }

    override fun openInBrowser(url: String) {
        context.browse(url, true)
    }

    override fun share(url: String) {
        context.share(url, getString(R.string.share_to))
    }

    private fun showTags(tags: List<String>) {
        for (tag in tags) {
            val textView = TextView(context)
            textView.background = ContextCompat.getDrawable(context, R.drawable.bg_shot_tag)
            textView.text = tag
            textView.gravity = Gravity.CENTER
            textView.setPadding(16, 0, 16, 0)
            tags_box.addView(textView)
        }
    }

    /**
     * The function [com.bumptech.glide.request.RequestListener.onResourceReady]
     * is called twice, so use a rgbs field to record the rgb values
     * to prevent duplicate.
     */
    private fun showPalette(palette: Palette) {
        for (swatch in palette.swatches) {
            if (!rgbs.contains(swatch.rgb)) {
                rgbs.add(swatch.rgb)
                val textView = TextView(context)
                textView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                textView.setBackgroundColor(swatch.rgb)
                val color = String.format("#%06X", (0xFFFFFF and swatch.rgb))
                textView.setOnClickListener {
                    Snackbar.make(fab, color, Snackbar.LENGTH_SHORT)
                            .setAction(getString(R.string.copy_to_clipboard), {
                                val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                manager.primaryClip = ClipData.newPlainText("text", color)
                                Snackbar.make(fab, R.string.copied, Snackbar.LENGTH_SHORT).show()
                            })
                            .show()
                }
                palette_layout.addView(textView)
            }
        }
    }

    private fun initView() {
        val act = activity as ShotActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}