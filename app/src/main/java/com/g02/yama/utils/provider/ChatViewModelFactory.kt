package com.g02.yama.utils.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g02.yama.repository.FirebaseRepository

class ChatViewModelFactory(val chatRepo: FirebaseRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass
                .getConstructor(FirebaseRepository::class.java)
                .newInstance(chatRepo)
    }

}
