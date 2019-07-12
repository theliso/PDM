package com.g02.yama.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.g02.yama.domain.UserMessages
import com.g02.yama.repository.FirebaseRepository
import com.g02.yama.repository.dataAccess.dtos.UserChatBoard

class ChatViewModel(private val repo: FirebaseRepository) : ViewModel() {

    lateinit var chatViewModel: MutableLiveData<ArrayList<UserChatBoard>>

    fun init(collection: String, document: String) {
        chatViewModel = repo.getMessages(collection, document)
    }

    fun getMessages() = chatViewModel

    fun updateMessages(collection: String, document: String)
            : MutableLiveData<ArrayList<UserChatBoard>> {
        return repo.updateMessages(collection, document)
    }

    fun updateDocument(msg: UserMessages, collection: String, document: String) {
        repo.updateDocument(msg, collection, document)
    }
}