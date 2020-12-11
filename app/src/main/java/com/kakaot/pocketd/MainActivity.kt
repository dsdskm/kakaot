package com.kakaot.pocketd

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "[PocketD]MainActivity"
    }

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext = this

        initView()

    }

    fun initView() {

        val searchView: SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d(TAG,"onQueryTextSubmit $query")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG,"onQueryTextChange $newText")
                return true
            }

        })
    }

}