package com.kakaot.pocketd.view

import android.content.Context
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.kakaot.pocketd.data.pkname.PkName

interface MainPresenter {
    interface IView {
        fun sendEvent()
    }

    interface IContents {
        fun initSearchView(searchView: SearchView)
        fun initRecyclerView(recyclerView: RecyclerView,context: Context)
        fun updateSearchResult(list:ArrayList<PkName>)
    }
}