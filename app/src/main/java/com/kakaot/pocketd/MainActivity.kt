package com.kakaot.pocketd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.pkname.PkViewModel
import com.kakaot.pocketd.view.MainViewPresenter
import com.kakaot.pocketd.view.MainPresenter

class MainActivity : AppCompatActivity(), MainPresenter.IView {

    companion object {
        const val TAG = "MainActivity"
    }

    private var mContext: Context? = null
    private var mMainViewPresenter: MainViewPresenter? = null

    private lateinit var mPkViewModel: PkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        mContext = this
        initViewModel()
        initView()
    }

    private fun initViewModel() {
        mPkViewModel = ViewModelProvider(this).get(PkViewModel::class.java)
        mPkViewModel.mPkNameList.observe(
            this, { it ->
                mMainViewPresenter!!.updateSearchResult(it)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d(TAG, "onDestroy")
        mPkViewModel.mPkNameList.removeObservers(this)
    }

    private fun initView() {

        val recyclerView: RecyclerView = findViewById(R.id.rview)
        val searchView: SearchView = findViewById(R.id.searchView)

        mMainViewPresenter = MainViewPresenter(mContext!!, this@MainActivity, mPkViewModel)
        mMainViewPresenter!!.initRecyclerView(recyclerView, applicationContext)
        mMainViewPresenter!!.initSearchView(searchView)

    }

    override fun sendEvent() {
    }


}