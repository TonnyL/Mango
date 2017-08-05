package io.github.tonnyl.mango.ui.settings

import android.content.Context
import io.github.tonnyl.mango.glide.GlideApp
import io.github.tonnyl.mango.glide.MangoAppGlideModule
import io.github.tonnyl.mango.util.FileUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Callable

/**
 * Created by lizhaotailang on 2017/7/19.
 */

class SettingsPresenter(view: SettingsContract.View) : SettingsContract.Presenter {

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun computeCacheSize(context: Context) {
        val disposable = Observable.fromCallable<Long>(
                Callable<Long> {
                    return@Callable FileUtil.dirSize(GlideApp.getPhotoCacheDir(context.applicationContext, MangoAppGlideModule.CACHE_FILE_NAME))
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.updateCacheSize(it)
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

    override fun clearCache(context: Context) {
        val disposable = Observable.fromCallable(
                Callable {
                    return@Callable GlideApp.get(context.applicationContext).clearDiskCache()
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mView.updateCacheSize(0)
                }, {

                })
        mCompositeDisposable.add(disposable)
    }

}