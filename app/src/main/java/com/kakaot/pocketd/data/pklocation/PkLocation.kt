package com.kakaot.pocketd.data.pklocation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pklocation")
data class PkLocation(
    @PrimaryKey val pkLocationId: Int,
    @ColumnInfo(name = "lat") val lat: Double?,
    @ColumnInfo(name = "lng") val lng: Double?
)
