package io.github.tonnyl.mango.database

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import java.util.concurrent.atomic.AtomicBoolean


/**
 * Created by lizhaotailang on 2017/6/28.
 */

object DatabaseCreator {

    private var mDatabase: AppDatabase? = null

    private var mInitializing: AtomicBoolean = AtomicBoolean(true)
    private var mIsDbCreated: AtomicBoolean = AtomicBoolean(false)

    fun createDb(context: Context) {
        Log.d("DatabaseCreator", "Creating DB from " + Thread.currentThread().name)

        if (!mInitializing.compareAndSet(true, false)) {
            return
        }

        Thread(Runnable {
            mDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
                    .build()
            mIsDbCreated.set(true)

        }).start()

    }

    fun isDatabaseCreated(): Boolean {
        return mIsDbCreated.get()
    }

    fun getDatabase(): AppDatabase? {
        return mDatabase
    }

}