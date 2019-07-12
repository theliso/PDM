package com.g02.yama.utils.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.g02.yama.repository.TeamRepository

class TeamViewModelFactory(val repository: TeamRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TeamRepository::class.java).newInstance(repository)
    }
}