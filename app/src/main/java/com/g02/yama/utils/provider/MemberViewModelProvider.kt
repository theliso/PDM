package com.g02.yama.utils.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g02.yama.repository.MemberRepository

class MemberViewModelFactory(val repository: MemberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MemberRepository::class.java).newInstance(repository)
    }
}