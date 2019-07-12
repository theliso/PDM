package com.g02.yama.utils.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g02.yama.repository.UserRepository

class UserViewModelFactory(
        val usrRepo: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
                .getConstructor(UserRepository::class.java)
                .newInstance(usrRepo)
    }
}