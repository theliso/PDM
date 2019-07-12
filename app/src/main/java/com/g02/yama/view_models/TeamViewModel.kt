package com.g02.yama.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.g02.yama.domain.Team
import com.g02.yama.repository.TeamRepository

class TeamViewModel(private val repo: TeamRepository) : ViewModel() {

    lateinit var teamViewModel: LiveData<Array<Team>>

    fun init() {
        teamViewModel = repo.getTeams(repo.getOrg(), repo.getToken())
    }

    fun getTeams(): LiveData<Array<Team>> = teamViewModel

    fun getLogin() : String = repo.getLogin()
    fun getOrganization(): String = repo.getOrg()
}