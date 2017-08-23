package io.github.tonnyl.mango.ui.auth

import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.ui.main.MainActivity
import io.github.tonnyl.mango.util.Constants
import kotlinx.android.synthetic.main.fragment_auth.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

/**
 * Created by lizhaotailang on 2017/6/24.
 *
 * Main ui for the authentication screen.
 */

class AuthFragment : Fragment(), AuthContract.View {

    private lateinit var mPresenter: AuthContract.Presenter

    companion object {
        @JvmStatic
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.subscribe()

        button_get_started.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(ApiConstants.DRIBBBLE_AUTHORIZE_URL
                    + "?client_id=" + ApiConstants.CLIENT_ID
                    + "&redirect_uri=" + ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI
                    + "&scope=" + ApiConstants.DRIBBBLE_AUTHORIZE_SCOPE)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: AuthContract.Presenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    private fun navigateToMainActivity() {
        context.startActivity(context.intentFor<MainActivity>().newTask().clearTask())
    }

    override fun updateLoginStatus(accessToken: AccessToken) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(Constants.IS_USER_LOGGED_IN, true)
                .putString(Constants.ACCESS_TOKEN, Gson().toJson(accessToken, AccessToken::class.java))
                .apply()

        enableShortCuts()

        navigateToMainActivity()
    }

    override fun handleAuthCallback(intent: Intent?) {
        if (intent != null
                && intent.data != null
                && !TextUtils.isEmpty(intent.data.authority)
                && (ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI_HOST == intent.data.authority)) {
            setLoginIndicator(true)
            mPresenter.requestAccessToken(intent.data.getQueryParameter("code"))
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(id: Int) {
        showMessage(getString(id))
    }

    override fun setLoginIndicator(isLoading: Boolean) {
        if (isLoading) {
            progress_bar?.visibility = View.VISIBLE
            button_get_started?.visibility = View.GONE
        } else {
            progress_bar?.visibility = View.GONE
            button_get_started?.visibility = View.VISIBLE
        }
    }

    // Enable the App Shortcuts for devices running Android 7.1 and above.
    // See [https://developer.android.com/guide/topics/ui/shortcuts.html].
    private fun enableShortCuts() {

        // doFromSdk(Build.VERSION_CODES.N_MR1), not work, sad story
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val pop = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_POPULAR)
                    .apply {
                        setLongLabel(getString(R.string.popular))
                        setShortLabel(getString(R.string.popular))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_pop))
                        setIntent(
                                Intent(context.applicationContext, MainActivity::class.java)
                                        .apply {
                                            action = Constants.INTENT_ACTION_POPULAR
                                            addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                        }
                        )
                    }.build()

            val following = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_FOLLOWING)
                    .apply {
                        setLongLabel(getString(R.string.following))
                        setShortLabel(getString(R.string.following))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_following))
                        setIntent(
                                Intent(context.applicationContext, MainActivity::class.java)
                                        .apply {
                                            action = Constants.INTENT_ACTION_FOLLOWING
                                            addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                        }
                        )
                    }
                    .build()

            val recent = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_RECENT)
                    .apply {
                        setLongLabel(getString(R.string.recent))
                        setShortLabel(getString(R.string.recent))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_recent))
                        setIntent(
                                Intent(context.applicationContext, MainActivity::class.java)
                                        .apply {
                                            action = Constants.INTENT_ACTION_RECENT
                                            addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                        }
                        )
                    }
                    .build()

            val debuts = ShortcutInfo.Builder(context, Constants.SHORTCUT_ID_DEBUTS)
                    .apply {
                        setLongLabel(getString(R.string.debuts))
                        setShortLabel(getString(R.string.debuts))
                        setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_debuts))
                        setIntent(
                                Intent(context.applicationContext, MainActivity::class.java)
                                        .apply {
                                            action = Constants.INTENT_ACTION_DEBUTS
                                            addCategory(ShortcutInfo.SHORTCUT_CATEGORY_CONVERSATION)
                                        }
                        )
                    }
                    .build()

            activity.getSystemService(ShortcutManager::class.java).dynamicShortcuts = mutableListOf<ShortcutInfo>(pop, following, recent, debuts)

        }
    }

}