package com.kakaot.pocketd.data.pkname

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PkNameDao {
    @Query("SELECT * FROM pkname")
    fun getAll(): List<PkName>

    @Query("SELECT * FROM pkname WHERE pkNameId = :id ")
    fun getDataById(id: Int): PkName

    @Query("DELETE FROM pkname")
    fun deleteAll()

    @Insert
    fun insertAll(list: ArrayList<PkName>)

    @Delete
    fun delete(data: PkName)
}