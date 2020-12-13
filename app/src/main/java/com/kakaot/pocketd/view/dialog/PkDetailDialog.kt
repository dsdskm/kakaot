package com.kakaot.pocketd.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.kakaot.pocketd.R
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.data.pkname.PkViewModel
import com.kakaot.pocketd.network.NetworkManager

class PkDetailDialog(context: Context) : Dialog(context) {
    companion object {
        private const val TAG = "PkDetailDialog"
    }

    private lateinit var mNameView: TextView
    private lateinit var mHeightView: TextView
    private lateinit var mWeightView: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mMapButton: Button
    var mName: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_dialog)
        Logger.d(TAG, "onCreate")
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)
        mNameView = findViewById(R.id.name)
        mNameView.text = mName
    }

    fun update(data: PkName) {
        mName = data.name_kr
    }
}
