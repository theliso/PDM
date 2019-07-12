package com.g02.yama.utils

import android.content.Context
import androidx.work.Result
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.g02.yama.YAMAApp
import com.g02.yama.repository.remote_data_source.GitHubWebApi

class MemberDataManager(context : Context, params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        var db = (applicationContext as YAMAApp).db
        val memberDao = db.MemberDAO()
        val token = inputData.getString("token")
        val idTeam = inputData.getString("id")
        val headers = mapOf(Pair("Authorization", "token $token"))
        GitHubWebApi.getTeamMembers(idTeam!!, headers){
            runAsync {
                memberDao.insertMembers(it.toTeamMember().members)
            }
        }
        return Result.success()
    }
}