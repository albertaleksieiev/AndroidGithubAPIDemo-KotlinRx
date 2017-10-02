package com.alfacer.githubdemo.view.main

import android.os.Bundle

/**
 * Created by albert on 10/2/17.
 */

enum class MainFragment{
    USER_DETAIL,
    SEARCH
}
interface IMainActivity {
    fun setFragment(mainFragment: MainFragment, arguments: Bundle? = null)
}