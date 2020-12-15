package com.kakaot.pocketd.data.pklocation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PkLocationDao {

    @Query("SELECT * FROM pklocation WHERE id = :id ")
    fun getListById(id:Int): List<PkLocation>

    @Query("DELETE FROM pklocation")
    fun deleteAll()

    @Insert
    fun insertAll(list:ArrayList<PkLocation>)

    @Delete
    fun delete(data: PkLocation)
}