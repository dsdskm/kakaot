package com.kakaot.pocketd.data.pkdetail

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PkDetailDao {
    @Query("SELECT * FROM pkdetail")
    fun getAll(): List<PkDetail>

    @Query("DELETE FROM pkdetail")
    fun deleteAll()

    @Insert
    fun insert(data:PkDetail)

    @Delete
    fun delete(data: PkDetail)
}