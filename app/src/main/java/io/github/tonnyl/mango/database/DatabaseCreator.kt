/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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