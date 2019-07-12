package com.g02.yama.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.g02.yama.domain.User
import com.g02.yama.repository.UserRepository
import com.g02.yama.repository.dataAccess.dtos.UserLoginDto

class LoginViewModel(val userRepo: UserRepository) : ViewModel() {

    private lateinit var login : UserLoginDto
    private lateinit var mainModel : LiveData<User>

    fun getUser(): LiveData<User> = mainModel

    fun init(token: String, user: String, organization: String) {
        login = UserLoginDto(user, organization, token)
        mainModel = userRepo.getUser(user, token, organization)
    }

    fun setPeriodicWork(token : String, organization: String){
        userRepo.setPeriodicWork(token, organization)
    }

    fun CanSave() = userRepo.save(login)
}