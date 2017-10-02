package com.alfacer.githubdemo.viewmodel.search

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import com.alfacer.githubdemo.api.manager.GithubSearchRequestManager
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.view.main.IMainActivity
import com.alfacer.githubdemo.view.main.MainFragment
import com.alfacer.githubdemo.view.profile.UserProfileFragment
import com.alfacer.githubdemo.view.search.SearchFragment
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by albert on 10/1/17.
 */
class SearchViewModel(val fragment: SearchFragment) {
    val githubSearchRequestManager: GithubSearchRequestManager
    init{
        githubSearchRequestManager = GithubSearchRequestManager(fragment.activity)
    }
    data class LatestSearchQuery(var query: String, var total_count: Int)
    var latestSearchQuery: LatestSearchQuery? = null
    var latestPage: Long = 1


    private val TAG: String? = "SEARCH_VEW MODEL"

    fun showUserProfile(user: User){
        if(fragment.activity is IMainActivity){
            val arguments = Bundle()
            arguments.putSerializable(UserProfileFragment.EXTRA_USER, user)
            (fragment.activity as IMainActivity)
                    .setFragment(MainFragment.USER_DETAIL, arguments)
        }
    }

    fun loadMoreSearchResults( onNewData: ((List<User>) -> Unit)? = null,
                               onError: ((Throwable) -> Unit) ?= null){
        latestSearchQuery?.let{
            githubSearchRequestManager.searchUser(latestSearchQuery!!.query,
                    latestPage + 1)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.items }
                    .doOnComplete { latestPage++ }
                    .subscribe({
                        onNewData?.invoke(it)
                    },
                    {
                        onError?.invoke(it)
                    })
        }
    }


    fun bindSearchViewAndGetResult(searchView: SearchView,
                                   onNewData: ((List<User>) -> Unit)? = null,
                                   onError: ((Throwable) -> Unit) ?= null ) {
        RxSearchView.queryTextChanges(searchView)
                .compose(fragment.bindToLifecycle())
                ?.debounce(300, TimeUnit.MILLISECONDS)
                ?.doOnError { Log.d(TAG,it.message ) }
                ?.flatMap {
                    query ->
                        githubSearchRequestManager
                                .searchUser(query.toString())
                                .subscribeOn(Schedulers.newThread())
                                .doOnNext {
                                    latestPage = 1
                                    latestSearchQuery = LatestSearchQuery(query.toString(), it.total_count)
                                }
                                ?.doOnError { Log.d(TAG,it.message ) }
                }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    latestPage = 1
                    onNewData?.invoke(it.items)
                },
                {
                    onError?.invoke(it)
                })
    }
}