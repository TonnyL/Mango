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

package io.github.tonnyl.mango.retrofit

import android.content.Context
import io.github.tonnyl.mango.data.AccessToken
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lizhaotailang on 2017/6/26.
 *
 * To custom the [Retrofit]. All the http request should be created by it.
 * You should be noted that you must [register your application](https://dribbble.com/account/applications/new)
 * and authenticate with either OAuth or your API client access token when making requests.
 * Before doing so, be sure to read [Dribbble][https://dribbble.com]'s [Terms & Guidelines](http://developer.dribbble.com/terms/)
 * to learn how the API may be used.
 *
 * See [http://developer.dribbble.com/] for the complete Dribbble developer guide and
 * [http://developer.dribbble.com/v1/] for the detailed API docs.
 *
 * Written by gejiaheng, modified by lizhaotailang.
 * See [See https://github.com/gejiaheng/Protein/blob/master/app/src/main/java/com/ge/protein/data/api/ServiceGenerator.java].
 */
object RetrofitClient {

    // The latest token
    private var mLastToken: String? = null
    // The [retrofit2.Retrofit] instance for whole app.
    private var mRetrofit: Retrofit? = null

    private var cache: Cache? = null

    fun init(context: Context) {
        cache?.let {
            throw IllegalStateException("Retrofit cache already initialized.")
        }
        cache = Cache(context.cacheDir, 20 * 1024 * 1024)
    }

    fun <T> createService(serviceClass: Class<T>, accessToken: AccessToken?): T {
        val currentToken: String = accessToken?.accessToken ?: ApiConstants.CLIENT_ACCESS_TOKEN

        if (mRetrofit == null || currentToken != mLastToken) {
            mLastToken = currentToken

            // Custom the http client.
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor { chain ->
                val original = chain.request()

                // Custom the request header.
                // See [http://developer.dribbble.com/v1/#authentication] for more information.
                val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer" + " " + mLastToken)
                        .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }.cache(cache)

            // Set the corresponding convert factory and call adapter factory.
            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(ApiConstants.DRIBBBLE_V1_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            mRetrofit = retrofitBuilder
                    .client(httpClientBuilder.build())
                    .build()
        }

        return mRetrofit!!.create(serviceClass)
    }


}