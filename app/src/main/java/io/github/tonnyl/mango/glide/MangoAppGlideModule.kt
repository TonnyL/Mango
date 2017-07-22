package io.github.tonnyl.mango.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream

/**
 * Created by lizhaotailang on 2017/7/18.
 */

@GlideModule
class MangoAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)

        // 36m, memory cache size
        // default value: 24m
        val memoryCacheSize = 1024 * 1024 * 36
        builder?.setMemoryCache(LruResourceCache(memoryCacheSize))

        val MAX_CACHE_SIZE = 1024 * 1024 * 512
        val CACHE_FILE_NAME = "IMG_CACHE"
        builder?.setDiskCache(ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))

        // Path: /sdcard/Android/data/<application package name>/cache/IMG_CACHE
        builder?.setDiskCache(ExternalCacheDiskCacheFactory(context, CACHE_FILE_NAME, MAX_CACHE_SIZE))
    }

    override fun registerComponents(context: Context?, registry: Registry?) {
        super.registerComponents(context, registry)
        // Replace the httpconnection with okhttp
        registry?.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

}