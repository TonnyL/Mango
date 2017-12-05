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

package io.github.tonnyl.mango.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * Created by lizhaotailang on 2017/7/18.
 *
 * To generated glide apis.
 */

@GlideModule
class MangoAppGlideModule : AppGlideModule() {

    companion object {
        // Max cache size of glide.
        @JvmField
        val MAX_CACHE_SIZE = 1024 * 1024 * 512 // 512M

        // The cache directory name.
        @JvmField
        val CACHE_FILE_NAME = "IMG_CACHE" // cache file dir name

    }

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)

        // 36MB, memory cache size
        // default value: 24MB
        val memoryCacheSize = 1024 * 1024 * 36
        builder?.setMemoryCache(LruResourceCache(memoryCacheSize))

        // Internal cache
        builder?.setDiskCache(InternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))
    }

    override fun registerComponents(context: Context?, glide: Glide?, registry: Registry?) {
        super.registerComponents(context, glide, registry)
        // Replace the http connection with okhttp
        registry?.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    /**
     * Disable the parsing of manifest file.
     */
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}