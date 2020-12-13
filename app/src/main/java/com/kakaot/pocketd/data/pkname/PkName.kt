package com.kakaot.pocketd.data.pkname

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pkname")
data class PkName(
    @PrimaryKey val pkNameId: Int,
    @ColumnInfo(name = "name_kr") val name_kr: String?,
    @ColumnInfo(name = "name_en") val name_en: String?
)
