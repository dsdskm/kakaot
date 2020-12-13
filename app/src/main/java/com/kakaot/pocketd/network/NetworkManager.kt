package com.kakaot.pocketd.network

import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_FRONT_DEFAULT
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_HEIGHT
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_ID
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_LAT
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_LNG
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_NAME
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_POKEMONS
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_SPRITES
import com.kakaot.pocketd.common.Constants.Companion.JSON_FIELD_WEIGHT
import com.kakaot.pocketd.common.Logger
import com.kakaot.pocketd.data.PkDatabaseManager
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.data.pkname.PkViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    // https://ijeee.tistory.com/27
    // https://namget.tistory.com/entry/RxKotlinRxJava-merge-zip-%EC%95%8C%EC%95%84%EB%B3%B4%EA%B8%B0
    private const val TAG = "NetworkManager"
    private const val URL_NAME_API = "https://demo0928971.mockable.io/";
    private const val URL_LOCATION_API = URL_NAME_API
    private const val URL_DETAIL_API = "https://pokeapi.co/"
    const val REQUEST_DATA_TYPE_NAME = 0
    const val REQUEST_DATA_TYPE_LOCATION = 1
    const val REQUEST_DATA_TYPE_DETAIL = 2

    fun requestData(req_type: Int, viewmodel: PkViewModel) {
        requestData(req_type, viewmodel, null)
    }

    fun _requestData(req_type: Int, viewmodel: PkViewModel, paramMap: Map<String, Any>?) {
        Logger.d(TAG, "_requestData")
        if (paramMap != null) {
            val id = paramMap["id"] as Int
            Logger.d(TAG, "_requestData id $id")
            val service1 = Retrofit.Builder().baseUrl(URL_LOCATION_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(IRetrofit::class.java)

            val service2 = Retrofit.Builder().baseUrl(URL_DETAIL_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(IRetrofit::class.java)

            val ob = Observable.zip(
                service1._getPocketmonLocation(),
                service2._getPocketmonDetail(id),
                BiFunction { r1, r2 ->
                    Logger.d(TAG, "res1 $r1")
                    Logger.d(TAG, "res2 $r2")
                }
            ).subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .onErrorReturn { e ->
                    e.printStackTrace()
                }
                .subscribe { it -> Logger.d(TAG, "ob it ${it}") }

        }
    }

    fun requestData(req_type: Int, viewmodel: PkViewModel, paramMap: Map<String, Any>?) {
        Logger.d(TAG, "requestData $req_type")
        val baseURL = when (req_type) {
            REQUEST_DATA_TYPE_NAME -> URL_NAME_API
            REQUEST_DATA_TYPE_LOCATION -> URL_LOCATION_API
            REQUEST_DATA_TYPE_DETAIL -> URL_DETAIL_API
            else -> {
                Logger.e(TAG, "request type is invalid")
                return
            }
        }

        Logger.d(TAG, "initRetrofit base url $baseURL")

        val service = Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(IRetrofit::class.java)

        val call = when (req_type) {
            REQUEST_DATA_TYPE_NAME -> service.getPocketmonName()
//            REQUEST_DATA_TYPE_LOCATION -> service.getPocketmonLocation()
            REQUEST_DATA_TYPE_DETAIL -> {
                if (paramMap != null) {
                    val id = paramMap["id"] as Int
                    service.getPocketmonDetail(id)
                } else {
                    Logger.e(TAG, "param is empty")
                    return
                }

            }
            else -> {
                Logger.e(TAG, "request type is invalid")
                return
            }
        }
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Logger.d("onFailure :: ", t!!.message!!)
            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                Logger.d("onResponse :: ", response.toString())
                Logger.d("onResponse :: ", response.body().toString())
                parseData(response.body().toString(), req_type, viewmodel)
            }
        })

    }

    private fun parseData(body: String, req_type: Int, viewmodel: PkViewModel) {
        when (req_type) {
            REQUEST_DATA_TYPE_NAME -> viewmodel.mPkNameList.value = parsePkName(body)
            REQUEST_DATA_TYPE_LOCATION -> parsePkLocation(body)
            REQUEST_DATA_TYPE_DETAIL -> parsePkDetail(body)
        }

    }

    private fun parsePkDetail(body: String) {
        //https://pokeapi.co/api/v2/pokemon/{id }
        val id = JSONObject(body)[JSON_FIELD_ID] as Int
        val height = JSONObject(body)[JSON_FIELD_HEIGHT] as Int
        val weight = JSONObject(body)[JSON_FIELD_WEIGHT] as Int
        val sprites = JSONObject(body)[JSON_FIELD_SPRITES] as JSONObject
        val image = sprites[JSON_FIELD_FRONT_DEFAULT] as String
        Logger.d(
            TAG,
            "parsePkDetail id : $id , height : $height , weight : $weight , sprites : $sprites , image : $image"
        )

        val pkName: PkName = PkDatabaseManager.getPkName(id)
    }

    private fun parsePkLocation(body: String) {
        //https://demo0928971.mockable.io/pokemon_locations
        val pokemons: JSONArray = JSONObject(body)[JSON_FIELD_POKEMONS] as JSONArray
        Logger.d(TAG, "parsePkLocation poke len ${pokemons.length()}")
        val list: ArrayList<PkLocation> = ArrayList()
        for (i in 0..pokemons.length()) {
            val jobject: JSONObject = pokemons[i] as JSONObject;
            val id = jobject.get(JSON_FIELD_ID) as Int
            val lat = jobject.get(JSON_FIELD_LAT) as Double
            val lng = jobject.get(JSON_FIELD_LNG) as Double
            val data = PkLocation(id, lat, lng)
            list.add(data)
        }

    }

    private fun parsePkName(body: String): ArrayList<PkName> {
        //https://demo0928971.mockable.io/pokemon_name
        Logger.d(TAG, "parsePkName body $body")
        val pokemons: JSONArray = JSONObject(body)[JSON_FIELD_POKEMONS] as JSONArray
        Logger.d(TAG, "parsePkName poke len ${pokemons.length()}")
        val list: ArrayList<PkName> = ArrayList()
        for (i in 0 until pokemons.length()) {
            val jobject: JSONObject = pokemons[i] as JSONObject
            val id = jobject.get(JSON_FIELD_ID) as Int
            val nameArr = jobject[JSON_FIELD_NAME] as JSONArray
            val nameKr = nameArr[0] as String
            val nameEn = nameArr[1] as String
            val data = PkName(id, nameKr, nameEn)
            list.add(data)
        }
        Logger.d(TAG, "parsePkName list size ${list.size}")
        PkDatabaseManager.insertPkNameAll(list)
        return list
    }
}