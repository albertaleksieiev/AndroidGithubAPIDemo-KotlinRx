package com.alfacer.githubdemo.api.service

import android.content.Context
import com.alfacer.githubdemo.api.retrofit.GithubSearchAPI
import com.alfacer.githubdemo.model.github.SearchUserResult
import com.alfacer.githubdemo.model.github.User
import io.reactivex.Observable


/**
 * Created by albert on 10/1/17.
 */
class GithubSearchService {
    fun searchUser(query: String, context: Context, page: Long = 1, per_page: Long = 20): Observable<SearchUserResult>{




        return ApiBuilder.build(context)
                .create(GithubSearchAPI::class.java)
                .searchUser(query, page, per_page)


    }
    fun searchUserByUsername(username: String, context: Context): Observable<User>{
        return ApiBuilder.build(context)
                .create(GithubSearchAPI::class.java)
                .searchUserByUsername(username)
    }
}