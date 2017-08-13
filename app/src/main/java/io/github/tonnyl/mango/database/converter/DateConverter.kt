package io.github.tonnyl.mango.database.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by lizhaotailang on 2017/8/12.
 *
 * Type converter for [io.github.tonnyl.mango.data.User].
 * Converts [Date] to timestamp(Long) and back.
 */

class DateConverter {

    companion object {
        @JvmStatic
        @TypeConverter
        fun toDate(timestamp: Long) : Date  = Date(timestamp)

        @JvmStatic
        @TypeConverter
        fun toTimestamp(date: Date) : Long = date.time
    }

}