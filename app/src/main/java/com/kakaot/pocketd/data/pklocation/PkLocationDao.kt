package com.kakaot.pocketd.data.pklocation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PkLocationDao {
    @Query("SELECT * FROM pklocation")
    fun getAll(): List<PkLocation>

    @Insert
    fun insertAll(list:ArrayList<PkLocation>)

    @Delete
    fun delete(data: PkLocation)
}