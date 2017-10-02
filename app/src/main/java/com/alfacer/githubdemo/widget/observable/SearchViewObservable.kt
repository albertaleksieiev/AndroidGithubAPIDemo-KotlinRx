package com.alfacer.githubdemo.widget.observable

import android.support.v7.widget.SearchView
import io.reactivex.Observable

/**
 * Created by albert on 10/1/17.
 */
class SearchViewObservable {
    companion object {
        fun from(searchView: SearchView?): Observable<String>? {


            return Observable.create<String> { observer ->
                searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let {
                            observer.onNext(query)
                        }
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let {
                            observer.onNext(newText)
                        }
                        return false
                    }
                })
            }
        }
    }
}