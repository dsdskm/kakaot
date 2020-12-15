package com.kakaot.pocketd.data.pkname

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kakaot.pocketd.data.pkdetail.PkDetail

class PkViewModel : ViewModel() {
    val mPkNameList: MutableLiveData<ArrayList<PkName>> by lazy {
        MutableLiveData<ArrayList<PkName>>()
    }
    val mPkDetail : MutableLiveData<PkDetail> by lazy {
        MutableLiveData<PkDetail>()
    }
}