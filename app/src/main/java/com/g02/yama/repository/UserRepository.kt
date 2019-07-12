package com.g02.yama.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.g02.yama.domain.User
import com.g02.yama.repository.dataAccess.daos.MemberDAO
import com.g02.yama.repository.dataAccess.daos.TeamDAO
import com.g02.yama.repository.dataAccess.daos.UserDAO
import com.g02.yama.repository.dataAccess.dtos.UserLoginDto
import com.g02.yama.repository.remote_data_source.GitHubWebApi
import com.g02.yama.utils.TeamDataManager
import com.g02.yama.utils.UserDataManager
import java.util.concurrent.TimeUnit

class UserRepository(
        private val userDao: UserDAO,
        private val teamDao: TeamDAO,
        private val memberDao: MemberDAO,
        private val shared : MySharedPreferences,
        private val wm: WorkManager
) {

    fun getUser(login: String, token: String, org: String) : LiveData<User> {
        val liveData : LiveData<User> = userDao.getUser(login)
        scheduleWork(token, org)
        return liveData
    }

    private fun scheduleWork(token: String, org: String) {
        val tk = Data.Builder().putString("token", token).build()
        val organization = Data.Builder()
                .putString("organization", org)
                .putString("token", token).build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val userData = OneTimeWorkRequestBuilder<UserDataManager>()
                .setConstraints(constraints)
                .setInputData(tk)
                .build()
        val teamsData = OneTimeWorkRequestBuilder<TeamDataManager>()
                .setConstraints(constraints)
                .setInputData(organization)
                .build()
        wm.enqueue(userData)
        wm.enqueue(teamsData)
    }

    fun setPeriodicWork(token: String, org: String) {
        val tk = Data.Builder().putString("token", token).build()
        val org = Data.Builder()
                .putString("organization", org)
                .putString("token", token).build()
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val userData = PeriodicWorkRequestBuilder<UserDataManager>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(tk)
                .build()
        val teamsData = PeriodicWorkRequestBuilder<TeamDataManager>(15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .setInputData(org)
                .build()
        wm.enqueue(userData)
        wm.enqueue(teamsData)
    }

    fun getImage(url: String, token: String): MutableLiveData<Bitmap> {
        val headers = mapOf(Pair("Authorization", "token $token"))
        val liveData = MutableLiveData<Bitmap>()
        GitHubWebApi.getImage(url, headers) {
            liveData.value = it
        }
        return liveData
    }

    fun save(user: UserLoginDto){
        shared.save(user)
    }

    fun getLogin(): String {
        return shared.getLogin()
    }

    fun getToken(): String {
        return shared.getToken()
    }

    fun getOrg(): String {
        return shared.getOrganization()
    }

    fun delete() {
        memberDao.delete(memberDao.getAllMembers().value!!)
        userDao.delete(userDao.getAllUsers().value!!)
        teamDao.delete(teamDao.getAllTeams().value!!)
    }

    fun getAll() : LiveData<Array<User>> {
        return userDao.getAllUsers()
    }


}