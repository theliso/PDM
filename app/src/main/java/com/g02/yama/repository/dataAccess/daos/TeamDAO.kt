package com.g02.yama.repository.dataAccess.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.g02.yama.domain.Team


@Dao
interface TeamDAO {

    @Query("select * from Team")
    fun getAllTeams() : LiveData<Array<Team>>

    @Query("select * from Team where id in (:id)")
    fun getTeamById(id : Int) : LiveData<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeams(teams : Array<Team>)

    @Delete
    fun delete(teams: Array<Team>)

}