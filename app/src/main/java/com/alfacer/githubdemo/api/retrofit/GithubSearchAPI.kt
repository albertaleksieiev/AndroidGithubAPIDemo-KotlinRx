package com.alfacer.githubdemo.api.retrofit

import com.alfacer.githubdemo.api.NetworkingConstantsApi
import com.alfacer.githubdemo.model.github.SearchUserResult
import com.alfacer.githubdemo.model.github.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by albert on 10/1/17.
 */
interface GithubSearchAPI {
    @GET(NetworkingConstantsApi.SEARCH_ENDPOINT)
    fun searchUser(@Query("q") query: String,
                   @Query("page") page: Long = 1,
                   @Query("per_page") per_page: Long = 20): Observable<SearchUserResult>

    @GET(NetworkingConstantsApi.SEARCH_USER_BY_NAME_ENDPOINT)
    fun searchUserByUsername(@Path("username") username: String): Observable<User>
}