package com.alfacer.githubdemo.view.profile

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alfacer.githubdemo.R
import com.alfacer.githubdemo.databinding.FragmentUserprofileBinding
import com.alfacer.githubdemo.model.github.User
import com.alfacer.githubdemo.viewmodel.profile.UserProfileViewModel
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by albert on 10/2/17.
 */
class UserProfileFragment : RxFragment() {
    companion object Extra{
        const val EXTRA_USER:String  = "user"
    }

    var userprofileBinding: FragmentUserprofileBinding? = null
    var viewModel: UserProfileViewModel? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.userprofileBinding = DataBindingUtil.inflate<FragmentUserprofileBinding>(layoutInflater, R.layout.fragment_userprofile, container, false);


        return userprofileBinding?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val user = arguments.getSerializable("user") as User

        viewModel = UserProfileViewModel(this)
        viewModel?.getUser(user.login)
                ?.subscribe({userprofileBinding?.user = it},{})

        this.userprofileBinding?.handler = UserProfileHandler(activity)
        this.userprofileBinding?.user = user
    }
}