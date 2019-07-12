package com.g02.yama.repository.dataAccess.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.g02.yama.domain.Member
import com.g02.yama.domain.Team
import com.g02.yama.domain.User
import com.g02.yama.repository.dataAccess.daos.MemberDAO
import com.g02.yama.repository.dataAccess.daos.TeamDAO
import com.g02.yama.repository.dataAccess.daos.UserDAO

@Database(entities = arrayOf(User::class, Team::class, Member::class), version = 1)
abstract class GitHubDataBase : RoomDatabase() {
    abstract fun UserDao() : UserDAO
    abstract fun TeamDAO(): TeamDAO
    abstract fun MemberDAO(): MemberDAO
}


