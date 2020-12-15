package com.kakaot.pocketd.data

import androidx.room.Room.databaseBuilder
import com.kakaot.pocketd.PkAppClass
import com.kakaot.pocketd.data.pkdetail.PkDetail
import com.kakaot.pocketd.data.pklocation.PkLocation
import com.kakaot.pocketd.data.pkname.PkName

object PkDatabaseManager {

    private const val DATABASE_NAME = "pk.db"
    private val db by lazy {
        databaseBuilder(
            PkAppClass.AppContext,
            PkDatabase::class.java, DATABASE_NAME
        ).build()
    }

    fun getPkName(id: Int): PkName {
        return db.pkNameDao().getDataById(id)
    }

    fun insertPkNameAll(list: ArrayList<PkName>) {
        Thread(Runnable {
            db.pkNameDao().deleteAll()
            db.pkNameDao().insertAll(list)
        }).start()
    }

    fun getPkLocations(id:Int):ArrayList<PkLocation>{
        return db.pkLocationDao().getListById(id) as ArrayList<PkLocation>
    }

    fun insertPkLocationAll(list: ArrayList<PkLocation>) {
        Thread(Runnable {
            db.pkLocationDao().deleteAll()
            db.pkLocationDao().insertAll(list)
        }).start()
    }

}