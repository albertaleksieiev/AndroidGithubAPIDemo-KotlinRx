package com.alfacer.githubdemo.widget.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/**
 * Created by albert on 10/1/17.
 */

abstract class BaseRecyclerView<T> : RecyclerView {
    private var isLoading = false
    private val visibleThreshold = 5
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0

    constructor(context: Context) : super(context) {
        setCache()
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setCache()
        init()
    }

    private fun setCache() {
        setHasFixedSize(true)
        setItemViewCacheSize(30)
        isDrawingCacheEnabled = true
        drawingCacheQuality = View.DRAWING_CACHE_QUALITY_LOW
    }

    private fun init() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView!!.layoutManager == null
                        || recyclerView.layoutManager !is LinearLayoutManager
                        || isLoading)
                    return

                val linearLayoutManager = layoutManager as LinearLayoutManager

                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()


                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    isLoading = true
                    loadMore(Runnable { isLoading = false })
                }
            }
        })

    }

    abstract fun dataChange(data: ArrayList<T>)

    protected abstract fun loadMore(onloaded: Runnable)
}
