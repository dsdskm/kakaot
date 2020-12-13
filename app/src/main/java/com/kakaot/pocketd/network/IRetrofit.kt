package com.kakaot.pocketd.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IRetrofit {
    @GET("/pokemon_name")
    fun getPocketmonName(): Call<JsonObject>

    @GET("/pokemon_locations")
    fun getPocketmonLocation(): Call<JsonObject>

    @GET("/{id}")
    fun getPocketmonDetail(@Path("id") id:Int): Call<JsonObject>
}