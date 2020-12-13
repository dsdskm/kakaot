package com.kakaot.pocketd.data

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pkdetail.PkDetailDao
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pklocation.PkLocationDao
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.data.pkname.PkNameDao

@Database(entities = [PkName::class, PkLocation::class, PkDetail::class], version = 1)

abstract class PkDatabase : RoomDatabase() {
    //https://developer.android.com/training/data-storage/room?hl=ko#kotlin
    abstract fun pkNameDao(): PkNameDao
    abstract fun pkLocationDao(): PkLocationDao
    abstract fun pkDetailDao(): PkDetailDao
}