package com.alfacer.githubdemo.view.search.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfacer.githubdemo.R
import com.alfacer.githubdemo.databinding.SearchRecyclerviewItemBinding
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.widget.ClickableEffectTouchListener
import com.alfacer.githubdemo.widget.view.BaseRecyclerView

/**
 * Created by albert on 10/1/17.
 */
class SearchRecyclerView: BaseRecyclerView<User> {
    var onLoadMoreListener: OnLoadMoreListener ?= null
    var onUserDetailClicked: OnUserDetailClicked? = null

    constructor(context: Context) : super(context){
        this.adapter = SearchRecyclerViewAdapter()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        this.adapter = SearchRecyclerViewAdapter()
    }



    override fun dataChange(data:  ArrayList<User>) {
        if(this.adapter is SearchRecyclerViewAdapter)
            (this.adapter as SearchRecyclerViewAdapter).list = data
    }

    fun newData(data: List<User>?) {
        data?.let {
            if(this.adapter is SearchRecyclerViewAdapter)
                (this.adapter as SearchRecyclerViewAdapter).appendData(data)
        }
    }

    public override fun loadMore(onloaded: Runnable) {
        onLoadMoreListener?.OnLoadMore(onloaded)
    }

    interface OnLoadMoreListener{
        fun OnLoadMore(dataLoaded: Runnable)
    }
    interface OnUserDetailClicked{
        fun OnClick(user: User)
    }


    inner class SearchRecyclerViewAdapter: Adapter<SearchRecyclerViewAdapter.SearchViewHolder>() {
        inner class SearchViewHolder(itemView: View?) : ViewHolder(itemView) {
            var userDataBinding: SearchRecyclerviewItemBinding? = null

            init {
                userDataBinding = DataBindingUtil.bind(itemView)
            }
        }
        fun appendData(data: List<User>){
            this.list.addAll(data)
            notifyDataSetChanged()
        }
        var list: ArrayList<User> = ArrayList()
            set(value) {
                this.list.clear()
                notifyDataSetChanged()
                this.list.addAll(value)
                notifyDataSetChanged()
            }
        override fun onBindViewHolder(holder: SearchViewHolder?, position: Int) {
            val user = list[position]

            holder?.userDataBinding?.user = user
            holder?.itemView?.setOnTouchListener(ClickableEffectTouchListener(object: View.OnClickListener{
                override fun onClick(p0: View?) {
                    this@SearchRecyclerView.onUserDetailClicked?.OnClick(user)
                }
            }))
        }

        override fun getItemCount(): Int
                = list.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder?
        {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.search_recyclerview_item, parent, false)
            return SearchViewHolder(view)
        }
    }
}