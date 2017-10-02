package com.alfacer.githubdemo.widget.view

import android.content.Context
import android.util.AttributeSet
import com.alfacer.githubdemo.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

/**
 * Created by albert on 10/1/17.
 */
open class UriImageView : android.support.v7.widget.AppCompatImageView {


    var isLoaded = false
        protected set

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    fun setupSizes(requestCreator: RequestCreator): RequestCreator {
            return requestCreator.fit()
                    .centerCrop()
    }


    fun display(uri: String?) {
        uri?.let {
            setupSizes(Picasso.with(context).load(uri))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.loader)
                    .into(this@UriImageView, object : Callback {
                        override fun onSuccess() {
                            isLoaded = true
                        }

                        override fun onError() {
                            setupSizes(Picasso.with(context).load(uri))
                                    .placeholder(R.drawable.loader)
                                    .into(this@UriImageView, object : Callback {
                                        override fun onSuccess() {
                                            isLoaded = true
                                        }

                                        override fun onError() {

                                        }
                                    })
                        }
                    })
        }
    }

    public var uri: String? = null
        set(value) {
            field = value
            display(value)
        }
}
