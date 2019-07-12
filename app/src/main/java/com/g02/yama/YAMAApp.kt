package com.g02.yama

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.g02.yama.repository.*
import com.google.firebase.FirebaseApp
import com.g02.yama.repository.dataAccess.room.GitHubDataBase
import com.g02.yama.repository.remote_data_source.GitHubWebApi

class YAMAApp : Application() {

    lateinit var userRepo: UserRepository
        private set
    lateinit var teamRepo: TeamRepository
        private set
    lateinit var memberRepo: MemberRepository
        private set
    lateinit var sharedPref: MySharedPreferences
        private set
    lateinit var chatRepo: FirebaseRepository
        private set
    lateinit var db: GitHubDataBase
        private set
    lateinit var wm: WorkManager
        private set

    override fun onCreate() {
        super.onCreate()
        GitHubWebApi.init(this)
        wm = WorkManager.getInstance()
        db =
                Room.databaseBuilder(
                        this,
                        GitHubDataBase::class.java,
                        "GitHub_database"
                ).build()
        sharedPref = MySharedPreferences(this)
        userRepo = UserRepository(
                db.UserDao(),
                db.TeamDAO(),
                db.MemberDAO(),
                sharedPref,
                wm
        )
        teamRepo = TeamRepository(db.TeamDAO(), sharedPref)
        memberRepo = MemberRepository(
                db.MemberDAO(),
                db.UserDao(),
                db.TeamDAO(),
                sharedPref
        )
        FirebaseApp.initializeApp(this)
        chatRepo = FirebaseRepository()
    }
}