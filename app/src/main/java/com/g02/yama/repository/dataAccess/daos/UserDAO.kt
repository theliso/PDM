package com.g02.yama.repository.dataAccess.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.g02.yama.domain.User

@Dao
interface UserDAO {

    @Query("select * from User")
    fun getAllUsers() : LiveData<Array<User>>

    @Query("select * from User where login in (:login)")
    fun getUser(login: String) : LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : User)

    @Delete
    fun delete(user : Array<User>)
}