package com.g02.yama.repository.dataAccess.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.g02.yama.domain.Member


@Dao
interface MemberDAO {

    @Query("select * from Member")
    fun getAllMembers() : LiveData<Array<Member>>

    @Query("select * from Member where idTeam in (:idTeam)")
    fun getMembersByTeamId(idTeam: Int): LiveData<Array<Member>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMembers(members: Array<Member>)

    @Delete
    fun delete(members: Array<Member>)
}