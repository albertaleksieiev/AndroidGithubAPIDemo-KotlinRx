package com.alfacer.githubdemo.viewmodel.profile

import android.util.Log
import com.alfacer.githubdemo.api.manager.GithubSearchRequestManager
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.view.profile.UserProfileFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by albert on 10/2/17.
 */
class UserProfileViewModel(var fragment: UserProfileFragment) {
    val githubSearchRequestManager: GithubSearchRequestManager
    init{
        githubSearchRequestManager = GithubSearchRequestManager(fragment.activity)
    }

    private val TAG: String = "UserProfileViewModel"

    fun getUser(username: String): Observable<User>{
        return githubSearchRequestManager.
                searchUserByUsername(username)
                .compose(fragment.bindToLifecycle())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { Log.d(TAG, it.message) }
    }
}