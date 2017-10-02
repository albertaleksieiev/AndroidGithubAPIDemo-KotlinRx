package com.alfacer.githubdemo.api.manager

import android.content.Context
import com.alfacer.githubdemo.api.service.GithubSearchService
import com.alfacer.githubdemo.model.github.SearchUserResult
import com.alfacer.githubdemo.model.github.User
import io.reactivex.Observable


/**
 * Created by albert on 10/1/17.
 */
class GithubSearchRequestManager(val context: Context) {
    val githubSearchService = GithubSearchService()

    fun searchUser(query: String, page: Long = 1, per_page: Long = 20): Observable<SearchUserResult> {
        if(query.isNotEmpty())
            return githubSearchService
                    .searchUser(query, context, page, per_page)
                    .onErrorResumeNext(Observable.empty<SearchUserResult>())

        return Observable.just(SearchUserResult(0,true, ArrayList()))
    }
    fun searchUserByUsername(username: String): Observable<User> {
        if(username.isEmpty()){
            return Observable.empty()
        }
        return githubSearchService
                .searchUserByUsername(username, context)
                .onErrorResumeNext(Observable.empty<User>())
    }

}