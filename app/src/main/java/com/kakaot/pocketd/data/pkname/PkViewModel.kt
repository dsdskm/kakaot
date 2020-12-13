package com.kakaot.pocketd.data.pkname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PkViewModel : ViewModel() {
    val mPkNameList: MutableLiveData<ArrayList<PkName>> by lazy {
        MutableLiveData<ArrayList<PkName>>()
    }
}