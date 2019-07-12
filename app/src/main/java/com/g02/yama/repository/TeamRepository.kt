package com.g02.yama.repository

import androidx.lifecycle.LiveData
import com.g02.yama.domain.Team
import com.g02.yama.repository.dataAccess.daos.TeamDAO

class TeamRepository(private val teamDao: TeamDAO, private val sharedPref: MySharedPreferences) {

    fun getTeams(org: String, token: String): LiveData<Array<Team>> {
        return teamDao.getAllTeams()
    }

    fun getLogin(): String {
        return sharedPref.getLogin()
    }

    fun getToken(): String {
        return sharedPref.getToken()
    }

    fun getOrg(): String {
        return sharedPref.getOrganization()
    }

}