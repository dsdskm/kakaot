package com.kakaot.pocketd.data.pkdetail

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pkname.PkName

@Entity(tableName = "pkdetail")
data class PkDetail(
    @PrimaryKey val pkDetailId: Int,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "height") val height: Int?,
    @ColumnInfo(name = "weight") val weight: Int?,
    @Embedded val pkName: PkName?,
    @Embedded val pkLocation: PkLocation?

)
