package com.alfacer.githubdemo

import android.app.Application
import com.squareup.picasso.OkHttpDownloader
import com.squareup.picasso.Picasso


/**
 * Created by albert on 10/2/17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttpDownloader(this, Integer.MAX_VALUE.toLong()))
        val built = builder.build()
        Picasso.setSingletonInstance(built)
    }
}