package com.kakaot.pocketd.data

import androidx.room.Room.databaseBuilder
import com.kakaot.pocketd.PkAppClass
import com.kakaot.pocketd.data.pkdetail.PkDetail
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

    fun insertPkDetail(data: PkDetail) {
        Thread(Runnable {
            db.pkDetailDao().deleteAll()
            db.pkDetailDao().insert(data)
        }).start()
    }

}