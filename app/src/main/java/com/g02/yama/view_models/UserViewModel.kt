package com.g02.yama.view_models

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g02.yama.domain.User
import com.g02.yama.repository.UserRepository

class UserViewModel(private val repo: UserRepository) : ViewModel() {

    private lateinit var userViewModel: LiveData<User>
    var userImage: MutableLiveData<Bitmap> = MutableLiveData()


    fun init() {
        userViewModel = repo.getUser(repo.getLogin(), repo.getToken(), repo.getOrg())
    }

    fun getUser(): LiveData<User> = userViewModel

    fun setImagRequest(url: String) {
        val token = repo.getToken()
        userImage = repo.getImage(url, token)
    }

    fun getImage(): LiveData<Bitmap> = userImage


}