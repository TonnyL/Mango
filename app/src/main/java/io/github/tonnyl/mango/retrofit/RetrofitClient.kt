package io.github.tonnyl.mango.retrofit

import io.github.tonnyl.mango.data.AccessToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by lizhaotailang on 2017/6/26.
 */

object RetrofitClient {

    private var mLastToken: String? = null
    private var mRetrofit: Retrofit? = null


    fun <T> createService(serviceClass: Class<T>, accessToken: AccessToken?): T {
        val currentToken: String = accessToken?.accessToken ?: ApiConstants.CLIENT_ACCESS_TOKEN

        if (mRetrofit == null || currentToken != mLastToken) {
            mLastToken = currentToken
            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer" + " " + mLastToken)
                        .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
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