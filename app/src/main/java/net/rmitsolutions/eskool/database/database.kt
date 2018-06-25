package net.rmitsolutions.eskool.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.rmitsolutions.eskool.database.dao.SchoolDao
import net.rmitsolutions.eskool.database.dao.StudentDao
import net.rmitsolutions.eskool.models.SchoolAddress

/**
 * Created by Madhu on 22-Aug-17.
 */
@Database(entities = arrayOf(SchoolAddress::class), version = 2)
abstract class eSkoolDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun schoolDao(): SchoolDao
}
