package io.github.tonnyl.mango.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import io.github.tonnyl.mango.retrofit.ApiConstants
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.shots.MainActivity
import io.github.tonnyl.mango.util.Constants

import kotlinx.android.synthetic.main.fragment_auth.*

/**
 * Created by lizhaotailang on 2017/6/24.
 */

class AuthFragment: Fragment(), AuthContract.View {

    var mPresenter: AuthContract.Presenter? = null

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonGetStarted.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(ApiConstants.DRIBBBLE_AUTHORIZE_URL
                    + "?client_id=" + ApiConstants.CLIENT_ID
                    + "&redirect_uri=" + ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI
                    + "&scope=" + ApiConstants.DRIBBBLE_AUTHORIZE_SCOPE)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.subscribe()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.unsubscribe()
    }

    override fun setPresenter(presenter: AuthContract.Presenter) {
        mPresenter = presenter
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    private fun navigateToMainActivity() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun updateLoginStatus(accessToken: AccessToken) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putBoolean(Constants.IS_USER_LOGGED_IN, true)
        editor.putString(Constants.ACCESS_TOKEN, Gson().toJson(accessToken, AccessToken::class.java))
        editor.apply()

        navigateToMainActivity()
    }

    override fun handleAuthCallback(intent: Intent?) {
        if (intent != null
                && intent.data != null
                && !TextUtils.isEmpty(intent.data.authority)
                && (ApiConstants.DRIBBBLE_AUTHORIZE_CALLBACK_URI_HOST == intent.data.authority)) {
            setLoginIndicator(true)
            mPresenter!!.requestAccessToken(intent.data.getQueryParameter("code"))
        }
    }

    override fun showMessage(id: Int) {
        Toast.makeText(activity, id, Toast.LENGTH_SHORT).show()
    }

    override fun setLoginIndicator(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            buttonGetStarted.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            buttonGetStarted.visibility = View.VISIBLE
        }
    }

}