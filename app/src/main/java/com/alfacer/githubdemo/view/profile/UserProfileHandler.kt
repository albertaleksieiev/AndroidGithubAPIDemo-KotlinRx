package com.alfacer.githubdemo.view.profile

import android.content.Context
import android.view.View
import com.alfacer.githubdemo.model.github.User
import android.content.Intent
import android.net.Uri


/**
 * Created by albert on 10/2/17.
 */
class UserProfileHandler(var context: Context) {
    fun onUserBlogClick(view: View, user: User){
        var url = user.blog
        if(url != null && url.isNotEmpty()){
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://" + url
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            context.startActivity(browserIntent)

        }
    }
    fun onUserEmailClick(view: View, user: User){
        var emailIntent = Intent(android.content.Intent.ACTION_SEND);

        var email = user.email
        if(email!=null && email.isNotEmpty()) {
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(user.email))

            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        }
    }

}