package com.g02.yama.utils

import android.content.Context
import androidx.work.Result
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.g02.yama.YAMAApp
import com.g02.yama.repository.remote_data_source.GitHubWebApi

class UserDataManager(context : Context, params : WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val userDao = (applicationContext as YAMAApp).db.UserDao()
        val token = inputData.getString("token")
        val headers = mapOf(Pair("Authorization", "token $token"))
        GitHubWebApi.getUser(headers){
            runAsync {
                userDao.insert(it.toUser())
            }
        }
        return Result.success()
    }
}