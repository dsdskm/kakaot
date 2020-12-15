package com.kakaot.pocketd.data.pkdetail

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pkname.PkName

data class PkDetail(
    val id: Int,
    val image: String?,
    val height: Int?,
    val weight: Int?,
    val pkName: PkName?,
    val pkLocations: ArrayList<PkLocation>?
)
