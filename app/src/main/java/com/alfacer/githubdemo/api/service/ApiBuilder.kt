package com.alfacer.githubdemo.api.service


import android.content.Context
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient



/**
 * Created by albert on 9/29/17.
 */

class ApiBuilder {
    companion object{
        private const val BASE_URL = "https://api.github.com/"

        var instance: Retrofit? = null
        fun build(context: Context): Retrofit{
            if(instance == null){
                val cacheSize = 10 * 1024 * 1024L // 10 MB
                val cache = Cache(context.cacheDir, cacheSize)

                val okHttpClient = OkHttpClient.Builder()
                        .cache(cache)
                        .build()
               instance = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()
            }
            return instance!!
        }
    }
}
