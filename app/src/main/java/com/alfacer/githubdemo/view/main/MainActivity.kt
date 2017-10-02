package com.alfacer.githubdemo.view.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.alfacer.githubdemo.R
import com.alfacer.githubdemo.view.profile.UserProfileFragment
import com.alfacer.githubdemo.view.search.SearchFragment
import com.alfacer.githubdemo.widget.ClickableEffectTouchListener

class MainActivity : AppCompatActivity(), IMainActivity {
    inner class Toolbar(resId: Int){
        var toolbar: android.support.v7.widget.Toolbar? = null
        var toolbarTitle: TextView? = null
        var toolbarBackButton: View? = null
        init{
            toolbar = findViewById(resId)
            toolbarTitle = toolbar?.findViewById(R.id.toolbarTitle)

            setSupportActionBar(toolbar)
            toolbarBackButton = toolbar?.findViewById(R.id.toolbarBackButton)

            toolbarBackButton
                    ?.setOnTouchListener(ClickableEffectTouchListener(object: View.OnClickListener{
                        override fun onClick(view: View?) {
                            onBackPressed()
                        }
                    }))
        }

        fun setTitle(title: String){
            toolbarTitle?.text = title
        }
        fun setVisibilityButtonBack(visibility: Int){
            toolbarBackButton?.visibility = visibility
        }
    }
    var toolbar: Toolbar? = null

    fun changeToolbar(mainFragment: MainFragment){
        val title = when(mainFragment){
            MainFragment.SEARCH -> getString(R.string.search)
            MainFragment.USER_DETAIL -> getString(R.string.profile)
        }
        val visibility = when(mainFragment){
            MainFragment.SEARCH -> View.GONE
            MainFragment.USER_DETAIL -> View.VISIBLE
        }
        toolbar?.setTitle(title)
        toolbar?.setVisibilityButtonBack(visibility)
    }
    override fun setFragment(mainFragment: MainFragment, arguments: Bundle?) {
        val fragment = when(mainFragment){
            MainFragment.SEARCH -> SearchFragment()
            MainFragment.USER_DETAIL -> UserProfileFragment()
        }


        fragment.arguments = arguments

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(fragment::class.java.name)
                .commit()

        changeToolbar(mainFragment)

    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount <= 1) {
            finish()
            return
        }
        val fragment = when(supportFragmentManager
                .getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                .name){
            UserProfileFragment::class.java.name -> MainFragment.SEARCH
            else -> MainFragment.USER_DETAIL

        }

        supportFragmentManager.popBackStack()
        changeToolbar(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        toolbar = Toolbar(R.id.toolbar)


        setFragment(MainFragment.SEARCH)
    }
}
