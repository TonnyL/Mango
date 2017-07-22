package io.github.tonnyl.mango.shot

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
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.Shot
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.user.UserProfileActivity
import kotlinx.android.synthetic.main.fragment_shot.*
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class ShotFragment : Fragment(), ShotContract.View {

    private lateinit var mPresenter: ShotContract.Presenter

    companion object {

        val EXTRA_SHOT = "EXTRA_SHOT"

        fun newInstance(): ShotFragment {
            return ShotFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.setShot(activity.intent.getParcelableExtra(EXTRA_SHOT))
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

        layout_comments.setOnClickListener({
            mPresenter.navigateToComments()
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

    override fun showMessage(message: String?) {
        message?.let {
            Snackbar.make(fab, message, Snackbar.LENGTH_SHORT).show()
        } ?: run {
            Snackbar.make(fab, getString(R.string.something_wrong), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun show(shot: Shot) {
        // Show the shot image
        GlideLoader.loadHighQualityWithPalette(context, simple_drawee_view, shot.images.best(), object : GlideLoader.OnPaletteProcessCallback {
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
        comments_count.text = shot.commentsCount.toString()

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

    override fun navigateToUserProfile(user: User) {
        context.startActivity<UserProfileActivity>(UserProfileActivity.EXTRA_USER to user)
    }

    override fun navigateToComments(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_COMMENTS, ListActivity.EXTRA_ID to shotId)
    }

    override fun navigateToLikes(shotId: Long) {
        context.startActivity<ListActivity>(ListActivity.EXTRA_TYPE to ListActivity.TYPE_LIKES, ListActivity.EXTRA_ID to shotId)
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

    private fun showPalette(palette: Palette) {
        for (swatch in palette.swatches.take(palette.swatches.size / 4)) {
            val textView = TextView(context)
            textView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            textView.setBackgroundColor(swatch.rgb)
            val color = String.format("#%06X", (0xFFFFFF and swatch.rgb))
            textView.setOnClickListener {
                Snackbar.make(fab, color, Snackbar.LENGTH_SHORT)
                        .setAction(getString(R.string.copy_to_clipboard), {
                            val manager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            manager.primaryClip = ClipData.newPlainText("text", color)
                            showMessage(getString(R.string.copied))
                        })
                        .show()
            }
            palette_layout.addView(textView)
        }
    }

}