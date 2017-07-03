package io.github.tonnyl.mango.database.dao

import android.arch.persistence.room.*
import io.github.tonnyl.mango.data.User
import io.reactivex.Flowable

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * FROM user WHERE id = :arg0")
    fun query(id: Long): Flowable<User>

    @Query("SELECT * FROM user")
    fun queryAll(): Flowable<List<User>>

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

}