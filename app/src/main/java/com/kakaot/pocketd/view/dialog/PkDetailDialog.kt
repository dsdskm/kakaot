package com.kakaot.pocketd.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kakaot.pocketd.PkMapActivity
import com.kakaot.pocketd.R
import com.kakaot.pocketd.common.Constants.Companion.KEY_PK_ID
import com.kakaot.pocketd.common.Constants.Companion.KEY_PK_NAME
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.data.pkname.PkViewModel
import com.kakaot.pocketd.network.NetworkManager
import kotlinx.serialization.StringFormat

class PkDetailDialog(context: Context) : Dialog(context) {
    companion object {
        private const val TAG = "PkDetailDialog"
    }

    private lateinit var mNameView: TextView
    private lateinit var mHeightView: TextView
    private lateinit var mWeightView: TextView
    private lateinit var mImageView: ImageView
    private lateinit var mMapButton: Button
    private var mName: String? = null
    private var mPkLocations: ArrayList<PkLocation>? = ArrayList()
    private var mImage: String? = null
    private var mHeight: Int? = null
    private var mWeight: Int? = null
    private var mPkDetail: PkDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_dialog)
        Logger.d(TAG, "onCreate")
        setCanceledOnTouchOutside(true)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setDimAmount(0.5f)
        mNameView = findViewById(R.id.name)
        mMapButton = findViewById(R.id.map)
        mImageView = findViewById(R.id.image)
        mHeightView = findViewById(R.id.height)
        mWeightView = findViewById(R.id.weight)

        mNameView.text = mName
        mMapButton.isEnabled = mPkLocations!!.size > 0
        mMapButton.setOnClickListener {
            val intent = Intent(context, PkMapActivity::class.java)
            intent.putExtra(KEY_PK_ID, mPkDetail!!.id)
            intent.putExtra(KEY_PK_NAME, mPkDetail!!.pkName!!.name_kr)
            context.startActivity(intent)
        }
        mHeightView.text = String.format(context.getString(R.string.height), mHeight)
        mWeightView.text = String.format(context.getString(R.string.weight), mWeight)
        Glide.with(context).load(mImage).into(mImageView)
    }

    fun update(data: PkDetail) {
        Logger.d(TAG, "update $data")
        mName = data.pkName!!.name_kr
        mPkLocations = data.pkLocations
        mImage = data.image
        mHeight = data.height
        mWeight = data.weight
        mPkDetail = data

    }
}
