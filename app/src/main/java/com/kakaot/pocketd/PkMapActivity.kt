package com.kakaot.pocketd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.kakaot.pocketd.common.Constants
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.PkDatabaseManager
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pklocation.PkLocation

class PkMapActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        private const val TAG = "PkMapActivity"
    }

    private var mGoogleMap: GoogleMap? = null
    private var mPkLocations: ArrayList<PkLocation>? = null
    private var mPkName: String? = null
    private var mPkId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pk_map)
        mPkId = intent.getIntExtra(Constants.KEY_PK_ID, 0)
        mPkName = intent.getStringExtra(Constants.KEY_PK_NAME)

        actionBar.let {
            title = mPkName
        }
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onMapReady(map: GoogleMap?) {
        mGoogleMap = map
        Thread {
            Looper.prepare()
            mPkLocations = PkDatabaseManager.getPkLocations(mPkId!!)
            mHandler.post(Runnable {
                addMarker()
            })
            Looper.loop()
        }.start()
    }

    var mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

        }
    }


    fun addMarker() {
        var latlng:LatLng? = null
        for (i in 0 until mPkLocations!!.size) {
            val location = mPkLocations!![i]
            val mo = MarkerOptions()
            latlng = LatLng(location.lat!!, location.lng!!)
            mo.position(latlng)
            mo.title(mPkName)
            mGoogleMap!!.addMarker(mo)
        }
        mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 7f))
    }

}