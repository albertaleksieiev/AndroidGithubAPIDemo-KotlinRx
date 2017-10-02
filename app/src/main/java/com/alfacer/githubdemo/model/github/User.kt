package com.alfacer.githubdemo.model.github

import java.io.Serializable

/**
 * Created by albert on 10/1/17.
 */
data class User(val login: String, val id: Long, val avatar_url: String,
                var followers: Int, val following: Int,
                var public_repos: Int,
                var location: String? = "",
                var blog: String? = "",
                var email: String? = "",
                var bio: String? = ""): Serializable
data class SearchUserResult(val total_count: Int, val incomplete_results: Boolean, val items: List<User>)