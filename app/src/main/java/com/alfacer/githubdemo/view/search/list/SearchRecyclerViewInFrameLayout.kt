package com.alfacer.githubdemo.view.search.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.alfacer.githubdemo.R
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.widget.view.BaseView

/**
 * Created by albert on 10/1/17.
 */
class SearchRecyclerViewInFrameLayout : BaseView {
    constructor(context: Context?) : super(context){
        initView()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }

    var mRecyclerView: SearchRecyclerView? = null
    var mTextViewEmpty: TextView? = null

    fun initView(){
        mRecyclerView = findViewById(R.id.recyclerView)
        mTextViewEmpty = findViewById(R.id.textViewEmpty)

        mRecyclerView?.let{
            val llm = LinearLayoutManager(context)
            llm.orientation = LinearLayoutManager.VERTICAL
            mRecyclerView?.setLayoutManager(llm)
        }
        showEmptyText()
    }
    override fun getLayoutResId(): Int = R.layout.search_recyclerview

    private fun showRecyclerView(){
        mRecyclerView?.visibility = View.VISIBLE
        mTextViewEmpty?.visibility = View.INVISIBLE
    }
    private fun showEmptyText(){
        mRecyclerView?.visibility = View.INVISIBLE
        mTextViewEmpty?.visibility = View.VISIBLE
    }

    var onLoadMoreListener: SearchRecyclerView.OnLoadMoreListener? = null
        set(value) {
            mRecyclerView?.onLoadMoreListener = value
        }
    var onUserDetailClicked: SearchRecyclerView.OnUserDetailClicked? = null
        set(value){
            mRecyclerView?.onUserDetailClicked = value
        }


    fun newData(data: List<User>?) {
        mRecyclerView?.newData(data)
        data?.let {
            if(data.isNotEmpty()){
                showRecyclerView()
            }
        }
    }
    fun dataChange(data: ArrayList<User>) {
        mRecyclerView?.dataChange(data)
        if(data.isEmpty()){
            showEmptyText()
        }else{
            showRecyclerView()
        }
    }
}