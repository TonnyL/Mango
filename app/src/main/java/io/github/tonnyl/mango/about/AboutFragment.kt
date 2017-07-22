package io.github.tonnyl.mango.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import io.github.tonnyl.mango.R
import kotlinx.android.synthetic.main.fragment_licenses.*

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class AboutFragment : Fragment(), AboutContract.View {

    private lateinit var mPresenter: AboutContract.Presenter

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater?.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        mPresenter.subscribe()

        /*childFragmentManager.beginTransaction()
                .add()*/
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

    override fun setPresenter(presenter: AboutContract.Presenter) {
        mPresenter = presenter
    }

    private fun initViews() {
        val act = activity as AboutActivity
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitle(R.string.about)
    }

}