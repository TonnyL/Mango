package io.github.tonnyl.mango.about.license

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import io.github.tonnyl.mango.R
import kotlinx.android.synthetic.main.fragment_licenses.*

/**
 * Created by lizhaotailang on 2017/7/21.
 */

class LicensesFragment : Fragment(), LicensesContract.View {

    private lateinit var mPresenter: LicensesContract.Presenter

    companion object {
        fun newInstance(): LicensesFragment {
            return LicensesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_licenses, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

        web_view.loadUrl("file:///android_asset/licenses.html")
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

    override fun setPresenter(presenter: LicensesContract.Presenter) {
        mPresenter = presenter
    }

    private fun initViews() {
        val settings = web_view.settings
        settings.javaScriptEnabled = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.defaultTextEncodingName = "UTF-8"
        settings.blockNetworkImage = false
        settings.domStorageEnabled = true
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView?, request: WebResourceRequest?): Boolean {
                webView?.loadUrl(request?.url.toString())
                return true
            }
        }

        val act = activity as LicensesActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = getString(R.string.open_source_licenses)
    }

}