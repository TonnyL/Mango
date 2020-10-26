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
import io.github.tonnyl.mango.databinding.FragmentShotBinding
import io.github.tonnyl.mango.glide.GlideLoader
import io.github.tonnyl.mango.glide.OnPaletteProcessingCallback
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

    private var _binding:FragmentShotBinding? = null
    private val binding get() = _binding!!

    companion object {

        @JvmStatic
        fun newInstance(): ShotFragment {
            return ShotFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        _binding= FragmentShotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        initView()

        binding.fab.setOnClickListener({
            mPresenter.toggleLike()
        })

        binding.avatar.setOnClickListener({
            mPresenter.navigateToUserProfile()
        })

        binding.buttonLikes.setOnClickListener({
            mPresenter.navigateToLikes()
        })

        binding.buttonComments.setOnClickListener({
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
            android.R.id.home -> activity?.onBackPressed()
            R.id.action_open_in_browser -> mPresenter.openInBrowser()
            R.id.action_share -> mPresenter.share()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setPresenter(presenter: ShotContract.Presenter) {
        mPresenter = presenter
    }

    override fun showNetworkError() {
        Snackbar.make(binding.fab, R.string.network_error, Snackbar.LENGTH_SHORT).show()
    }

    override fun show(shot: Shot) {
        // Show the menu_shot image
        GlideLoader.loadHighQualityWithPalette(binding.shotImageView, shot.images.best(), object : OnPaletteProcessingCallback {
            override fun onPaletteGenerated(palette: Palette?) {
                palette?.let {
                    showPalette(palette)
                } ?: run {
                    binding.paletteLayout.visibility = View.GONE
                }
            }

            override fun onPaletteNotAvailable() {
                context?.runOnUiThread {
                    binding.paletteLayout.visibility = View.GONE
                }
            }
        })

        GlideLoader.loadAvatar(binding.avatar, shot.user?.avatarUrl)

        binding.toolbar.title = shot.title

        if (shot.description != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                binding.shotDescription.text = Html.fromHtml(shot.description, Html.FROM_HTML_MODE_LEGACY)
            } else {
                binding.shotDescription.text = Html.fromHtml(shot.description)
            }
            binding.shotDescription.movementMethod = LinkMovementMethod.getInstance()
        } else {
            binding.shotDescription.visibility = View.GONE
        }

        binding.name.text = shot.user?.name
        binding.userName.text = shot.user?.username
        binding.createdTime.text = DateUtils.getRelativeTimeSpanString(shot.createdAt.time, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)

        binding.buttonLikes.text = getString(R.string.likes_formatted).format(shot.likesCount)
        binding.buttonViews.text = getString(R.string.views_formatted).format(shot.viewsCount)
        binding.buttonComments.text = getString(R.string.comments_formatted).format(shot.commentsCount)

        // Show the tags
        showTags(shot.tags)
    }

    override fun setLikeStatus(like: Boolean) {
        binding.fab.setImageResource(if (like) {
            R.drawable.ic_favorite_white_24dp
        } else {
            R.drawable.ic_favorite_border_white_24dp
        })
    }

    override fun updateLikeCount(count: Int) {
        binding.buttonLikes.text = getString(R.string.likes_formatted).format(count)
    }

    override fun navigateToUserProfile(user: User) {
        context?.startActivity<UserProfileActivity>(UserProfilePresenter.EXTRA_USER to user)
    }

    override fun navigateToComments(shot: Shot) {
        context?.startActivity<CommentsActivity>(CommentsPresenter.EXTRA_SHOT to shot)
    }

    override fun navigateToLikes(shot: Shot) {
        context?.startActivity<LikesActivity>(LikesPresenter.EXTRA_SHOT to shot)
    }

    override fun openInBrowser(url: String) {
        context?.browse(url, true)
    }

    override fun share(url: String) {
        context?.share(url, getString(R.string.share_to))
    }

    private fun showTags(tags: List<String>) {
        for (tag in tags) {
            context?.let {
                with(it) {
                    val textView = TextView(context)
                    textView.background = ContextCompat.getDrawable(this, R.drawable.bg_shot)
                    textView.text = tag
                    textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
                    textView.gravity = Gravity.CENTER
                    textView.setPadding(16, 8, 16, 8)
                    tags_box.addView(textView)
                }
            }

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
                                val manager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                manager.primaryClip = ClipData.newPlainText("text", color)
                                Snackbar.make(binding.fab, R.string.copied, Snackbar.LENGTH_SHORT).show()
                            })
                            .show()
                }
                binding.paletteLayout.addView(textView)
            }
        }
    }

    private fun initView() {
        val act = activity as ShotActivity
        act.setSupportActionBar(binding.toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
