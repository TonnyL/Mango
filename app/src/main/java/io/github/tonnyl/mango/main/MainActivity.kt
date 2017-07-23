package io.github.tonnyl.mango.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.tonnyl.mango.R
import io.github.tonnyl.mango.injection.module.MainPresenterModule
import io.github.tonnyl.mango.util.AccountManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment

    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        savedInstanceState?.let {
            mainFragment = fragmentManager.getFragment(it, MainFragment::class.java.simpleName) as MainFragment
        } ?: run {
            mainFragment = MainFragment.newInstance()
        }

        if (AccountManager.accessToken == null) {
            AccountManager.init(this)
        }

        supportFragmentManager.beginTransaction()
                .add(R.id.container, mainFragment, MainFragment::class.java.simpleName)
                .commit()

        DaggerMainComponent.builder()
                .mainPresenterModule(MainPresenterModule(mainFragment))
                .build()
                .inject(this@MainActivity)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (mainFragment.isAdded) {
            supportFragmentManager.putFragment(outState, MainFragment::class.java.simpleName, mainFragment)
        }
    }

}
