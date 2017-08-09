package io.github.tonnyl.mango.ui.main

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.AuthUserRepository
import io.github.tonnyl.mango.util.AccountManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/6/28.
 *
 * Listens the user action from the ui [io.github.tonnyl.mango.ui.main.MainFragment],
 * retrieves the data and update the ui as required.
 */

class MainPresenter(view: MainContract.View) : MainContract.Presenter {

    private var mView: MainContract.View = view
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mUser: User? = null

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        fetchUser()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun fetchUser() {
        val disposable = AuthUserRepository.getAuthenticatedUser(AccountManager.accessToken?.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    mUser = response
                    mView.showAuthUserInfo(response)
                })
        mCompositeDisposable.add(disposable)
    }

    override fun logoutUser() {
        AccountManager.accessToken?.let {
            val disposable = AuthUserRepository.getAuthenticatedUser(it.id)
                    .subscribeOn(Schedulers.io())
                    .subscribe({
                        AuthUserRepository.deleteAuthenticatedUser(it)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    mView.navigateToLogin()
                                }, {

                                })
                    })
            mCompositeDisposable.add(disposable)
        }
    }

    override fun getUser(): User? {
        return mUser
    }

}