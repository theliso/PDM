package com.g02.yama.repository

import androidx.lifecycle.MutableLiveData
import com.g02.yama.domain.UserMessages
import com.g02.yama.repository.dataAccess.dtos.UserChatBoard
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {

    private val fb = FirebaseFirestore.getInstance()

    fun getMessages(collection: String, document: String)
            : MutableLiveData<ArrayList<UserChatBoard>> {
        val liveData: MutableLiveData<ArrayList<UserChatBoard>> = MutableLiveData()
        val firebaseData = fb.collection(collection).document(document)
        firebaseData.get().continueWith {
            ArrayList(it.result?.toObject(UserMessages::class.java)?.messages.orEmpty())
        }.continueWith {
            liveData.value = it.result
        }
        return liveData
    }

    fun updateMessages(collection: String, document: String)
            : MutableLiveData<ArrayList<UserChatBoard>> {
        val liveData: MutableLiveData<ArrayList<UserChatBoard>> = MutableLiveData()
        val firebaseData = fb.collection(collection).document(document)
        firebaseData.addSnapshotListener {
            documentSnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException != null)
                return@addSnapshotListener
            if (documentSnapshot != null && documentSnapshot.exists()){
                val messages = documentSnapshot.toObject(UserMessages::class.java)?.messages
                liveData.value = messages
            }
        }
        return liveData
    }

    fun updateDocument(msg: UserMessages, collection: String, document: String) {
        fb.collection(collection).document(document).set(msg)
    }


}
