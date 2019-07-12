package com.g02.yama.utils

import android.content.Context
import androidx.work.*
import com.g02.yama.YAMAApp
import com.g02.yama.domain.Team
import com.g02.yama.repository.remote_data_source.GitHubWebApi

class TeamDataManager(context : Context, params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val db = (applicationContext as YAMAApp).db
        val teamDao = db.TeamDAO()
        val token = inputData.getString("token")
        val org = inputData.getString("organization")
        val headers = mapOf(Pair("Authorization", "token $token"))
        GitHubWebApi.getTeams(org!!, headers){
            runAsync {
                val teams = it.toOrganization().teams
                teamDao.insertTeams(teams)
                scheduleTeamsMembers(teams, token)
            }
        }
        return Result.success()
    }

    private fun scheduleTeamsMembers(teams: Array<Team>, token: String?) {
        val tk = Data.Builder().putString("token", token)
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        var manager = (applicationContext as YAMAApp).wm
        teams.forEach {
            tk.putString("id", it.id.toString())
            val userData = OneTimeWorkRequestBuilder<MemberDataManager>()
                    .setConstraints(constraints)
                    .setInputData(tk.build())
                    .setInputData(tk.build())
                    .build()
            manager.enqueue(userData)
        }
    }
}