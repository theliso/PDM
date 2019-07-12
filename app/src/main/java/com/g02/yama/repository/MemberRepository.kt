package com.g02.yama.repository

import androidx.lifecycle.LiveData
import com.g02.yama.domain.Member
import com.g02.yama.domain.Team
import com.g02.yama.domain.User
import com.g02.yama.repository.dataAccess.daos.MemberDAO
import com.g02.yama.repository.dataAccess.daos.TeamDAO
import com.g02.yama.repository.dataAccess.daos.UserDAO
import com.g02.yama.utils.runAsync
import java.lang.Integer.parseInt

class MemberRepository(private val memberDao: MemberDAO,
                       private val userDao: UserDAO,
                       private val teamDao: TeamDAO,
                       private val sharedPref: MySharedPreferences) {

    fun getMember(id: String): LiveData<Array<Member>> {
        return memberDao.getMembersByTeamId(parseInt(id))
    }

    fun getAllUsers(): LiveData<Array<User>> = userDao.getAllUsers()

    fun getAllTeams(): LiveData<Array<Team>> = teamDao.getAllTeams()

    fun getAllMembers() : LiveData<Array<Member>> = memberDao.getAllMembers()

    fun deleteUsers(users: Array<User>) {
        runAsync {
            userDao.delete(users)
        }
    }

    fun deleteTeams(teams: Array<Team>) =
            runAsync {
        teamDao.delete(teams)
    }

    fun deleteMembers(members: Array<Member>) =
            runAsync {
                memberDao.delete(members)
            }

    fun deleteSharedPreferences() = sharedPref.erase()



}