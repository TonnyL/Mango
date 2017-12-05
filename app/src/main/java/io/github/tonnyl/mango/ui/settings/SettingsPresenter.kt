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
 *
 * Listens to user action from the ui [io.github.tonnyl.mango.ui.settings.SettingsFragment],
 * retrieves the data and update the ui as required.
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