package com.alfacer.githubdemo.view.search

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfacer.githubdemo.R
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.view.search.list.SearchRecyclerView
import com.alfacer.githubdemo.view.search.list.SearchRecyclerViewInFrameLayout
import com.alfacer.githubdemo.viewmodel.search.SearchViewModel
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by albert on 10/1/17.
 */
class SearchFragment: RxFragment() {
    var searchView: SearchView? = null
    var searchRecyclerView: SearchRecyclerViewInFrameLayout? = null

    lateinit var viewModel: SearchViewModel



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_search, container, false)

        searchView = view?.findViewById(R.id.searchView)
        searchRecyclerView = view?.findViewById(R.id.searchRecyclerView)
        viewModel = SearchViewModel(this)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {


        searchView?.let{
            viewModel.bindSearchViewAndGetResult(searchView!!, {
                searchRecyclerView?.dataChange(it as ArrayList<User>)
            }){
                hadleSearchThrowable(it)
            }
        }
        searchRecyclerView?.onLoadMoreListener = object: SearchRecyclerView.OnLoadMoreListener{
            override fun OnLoadMore(dataLoaded: Runnable) {
                viewModel.loadMoreSearchResults({
                    dataLoaded.run()
                    searchRecyclerView?.newData(it)
                },
                {
                    dataLoaded.run()
                    hadleSearchThrowable(it)
                })
            }
        }
        searchRecyclerView?.onUserDetailClicked = object: SearchRecyclerView.OnUserDetailClicked{
            override fun OnClick(user: User) {
                viewModel.showUserProfile(user)
            }
        }

    }

    fun hadleSearchThrowable(throwable: Throwable){

    }
}