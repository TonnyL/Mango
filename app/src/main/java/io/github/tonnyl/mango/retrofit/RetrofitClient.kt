package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.AccessToken
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
 */
object RetrofitClient {

    // The latest token
    private var mLastToken: String? = null
    // The [retrofit2.Retrofit] instance for whole app.
    private var mRetrofit: Retrofit? = null

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
            }

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