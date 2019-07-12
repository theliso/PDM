package com.g02.yama.viewModelProvider

import android.app.Application
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.work.WorkManager
import com.g02.yama.YAMAApp
import com.g02.yama.repository.*
import com.g02.yama.utils.provider.*
import com.google.firebase.firestore.FirebaseFirestore

val Application.userRepo: UserRepository get() = (this as YAMAApp).userRepo

val Application.teamRepo: TeamRepository get() = (this as YAMAApp).teamRepo

val Application.memberRepo: MemberRepository get() = (this as YAMAApp).memberRepo

val Application.sharedPref: MySharedPreferences get() = (this as YAMAApp).sharedPref

val Application.wm : WorkManager get() = (this as YAMAApp).wm

val Application.fb : FirebaseFirestore get() = (this as YAMAApp).fb

val Application.chatRepo : FirebaseRepository get() = (this as YAMAApp).chatRepo


inline fun <reified T : ViewModel> FragmentActivity.userViewModel(): T =
        ViewModelProviders
                .of(this,
                        UserViewModelFactory(
                                this.application.userRepo
                        )
                )
                .get(T::class.java)


inline fun <reified T : ViewModel> FragmentActivity.teamViewModel(): T =
        ViewModelProviders
                .of(this, TeamViewModelFactory(this.application.teamRepo))
                .get(T::class.java)

inline fun <reified T : ViewModel> FragmentActivity.memberViewModel(): T =
        ViewModelProviders
                .of(this, MemberViewModelFactory(this.application.memberRepo))
                .get(T::class.java)

inline fun <reified T : ViewModel> FragmentActivity.loginViewModel(): T =
        ViewModelProviders
                .of(this,
                        LoginViewModelFactory(
                                this.application.userRepo
                        )
                )
                .get(T::class.java)

inline fun <reified T : ViewModel> FragmentActivity.chatViewModel(): T =
        ViewModelProviders
                .of(this,
                        ChatViewModelFactory(
                                this.application.chatRepo
                        )
                )
                .get(T::class.java)