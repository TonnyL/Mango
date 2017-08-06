package io.github.tonnyl.mango.ui.user.shots

import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.data.repository.ShotsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by lizhaotailang on 2017/7/19.
 */
class ShotsPresenter(view: ShotsContract.View, user: User) : ShotsContract.Presenter {

    private val mView = view
    private val mUser = user
    private val mCompositeDisposable: CompositeDisposable

    companion object {
        @JvmField
        val EXTRA_USER = "EXTRA_USER"
    }

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mView.setLoadingIndicator(true)
        val disposable = ShotsRepository.listShotsOfUser(mUser.id, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response.body()?.let {
                        for (shot in it) {
                            shot.user = mUser
                        }
                        mView.showShots(it.toMutableList())
                        mView.setLoadingIndicator(false)
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

}