package io.github.tonnyl.mango.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.tonnyl.mango.data.AccessToken
import io.github.tonnyl.mango.data.User
import io.github.tonnyl.mango.database.dao.AccessTokenDao
import io.github.tonnyl.mango.database.dao.UserDao

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@Database(entities = arrayOf(AccessToken::class, User::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        val DATABASE_NAME = "mango-db"
    }

    abstract fun accessTokenDao(): AccessTokenDao

    abstract fun userDao(): UserDao

}