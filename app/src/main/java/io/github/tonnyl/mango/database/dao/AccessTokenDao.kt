package io.github.tonnyl.mango.database.dao

import android.arch.persistence.room.*
import io.github.tonnyl.mango.data.AccessToken

/**
 * Created by lizhaotailang on 2017/6/28.
 */

@Dao
interface AccessTokenDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(accessToken: AccessToken)

    @Query("SELECT * FROM access_token WHERE id = :arg0")
    fun query(id: Long): AccessToken

    @Update
    fun update(accessToken: AccessToken)

    @Delete
    fun delete(accessToken: AccessToken)

}