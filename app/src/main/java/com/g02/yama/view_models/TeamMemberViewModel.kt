package com.g02.yama.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.g02.yama.domain.Member
import com.g02.yama.domain.Team
import com.g02.yama.domain.User
import com.g02.yama.repository.MemberRepository

class TeamMemberViewModel(private val repo: MemberRepository) : ViewModel() {

    lateinit var members: LiveData<Array<Member>>

    fun init(id: String) {
        members = repo.getMember(id)
    }

    fun getTeamMembers(): LiveData<Array<Member>> = members

    fun getAllUsers(): LiveData<Array<User>> = repo.getAllUsers()
    fun getAllTeams(): LiveData<Array<Team>> = repo.getAllTeams()
    fun getAllMembers(): LiveData<Array<Member>> = repo.getAllMembers()

    fun deleteUsers(users: Array<User>) = repo.deleteUsers(users)
    fun deleteTeams(teams: Array<Team>) = repo.deleteTeams(teams)
    fun deleteMembers(members: Array<Member>) = repo.deleteMembers(members)
    fun deleteSharedPreferences() = repo.deleteSharedPreferences()

}