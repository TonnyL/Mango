package io.github.tonnyl.mango.main

import io.github.tonnyl.mango.data.repository.UserRepository
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 */

class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    private val mView: MainContract.View = view
    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        fetchUser()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchUser() {
        val disposable = UserRepository.getAuthenticatedUser(AccountManager.authenticatedUser?.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mView.showAuthUserInfo(response)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun logoutUser() {
        AccountManager.authenticatedUser?.let {
            val disposable = UserRepository.deleteAuthenticatedUser(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ _ ->
                        mView.navigateToLogin()
                    })
            mCompositeDisposable.add(disposable)
        }
    }

}