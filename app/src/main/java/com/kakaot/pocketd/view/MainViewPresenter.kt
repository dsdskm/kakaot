package com.kakaot.pocketd.view

import android.content.Context
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakaot.pocketd.MainActivity
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.data.pkname.PkViewModel
import com.kakaot.pocketd.network.NetworkManager
import com.kakaot.pocketd.view.dialog.PkDetailDialog

class MainViewPresenter(
    activityContext: Context,
    view: MainPresenter.IView,
    pkViewModel: PkViewModel
) : MainPresenter.IContents {

    val TAG = "MainViewPresenter"
    var mMainView: MainPresenter.IView = view           // to send event to MainActivity
    var mPkViewModel: PkViewModel = pkViewModel
    var mActivityContext = activityContext
    private val mAdapter: RViewAdapter by lazy {
        RViewAdapter(activityContext, mIOnItemClick)
    }

    interface IOnItemClick {
        fun onClick(data: PkName)
    }

    val mIOnItemClick: IOnItemClick = object : IOnItemClick {
        override fun onClick(data: PkName) {
            val param: Map<String, Int> = mapOf("id" to data.id)
            NetworkManager.requestDetail(mPkViewModel, param)
        }
    }


    override fun initSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Logger.d(MainActivity.TAG, "onQueryTextSubmit $query")
                NetworkManager.requestData(NetworkManager.REQUEST_DATA_TYPE_NAME, mPkViewModel)
                searchView.clearFocus();
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Logger.d(MainActivity.TAG, "onQueryTextChange $newText")
                return true
            }
        })
    }

    override fun initRecyclerView(recyclerView: RecyclerView, context: Context) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter

    }

    override fun updateSearchResult(list: ArrayList<PkName>) {
        mAdapter.setList(list)
    }

    override fun showDetail(data: PkDetail) {
        Logger.d(TAG,"showDetail $data")
        val mDialog = PkDetailDialog(mActivityContext)
        mDialog.update(data)
        mDialog.show()
    }

}