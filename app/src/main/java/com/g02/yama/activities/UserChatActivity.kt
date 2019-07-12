package com.g02.yama.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g02.yama.R
import com.g02.yama.domain.UserMessages
import com.g02.yama.recycler_views.ChatRecyclerView
import com.g02.yama.repository.dataAccess.dtos.UserChatBoard
import com.g02.yama.viewModelProvider.chatViewModel
import com.g02.yama.viewModelProvider.memberViewModel
import com.g02.yama.view_models.ChatViewModel
import com.g02.yama.view_models.TeamMemberViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*


class UserChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val USER_COLLECTION = "UserChannels"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_chat)

        viewModel = this.chatViewModel()

        val userChannel = intent?.extras?.getString("Channel")!!
        val user = intent?.extras?.getString("User")!!

        val document = FirebaseFirestore
                .getInstance()
                .collection(USER_COLLECTION)
                .document(userChannel)

        viewModel.init(USER_COLLECTION, userChannel)

        viewAdapter = ChatRecyclerView(this, viewModel.chatViewModel)
        linearLayoutManager = LinearLayoutManager(this)

        viewModel.getMessages().observeForever{
            this.chatBoard.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = viewAdapter
            }
        }

        viewModel.updateMessages(USER_COLLECTION, userChannel).observeForever{
            if(it.isNotEmpty()){
                viewModel.chatViewModel.value = it
            }
        }

        setClickListeners(document, user, userChannel)
    }

    private fun setClickListeners(document: DocumentReference, user: String, userChannel: String) {
        backButton.setOnClickListener {
            val memberViewModel: TeamMemberViewModel
            memberViewModel = this.memberViewModel()
            memberViewModel.deleteSharedPreferences()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        infoButton.setOnClickListener {
            startActivity(Intent(this, UserInfoActivity::class.java))
            finish()
        }

        teamsButton.setOnClickListener {
            startActivity(Intent(this, TeamActivity::class.java))
            finish()
        }

        send.setOnClickListener {
            if (text.toString().isNotEmpty()) {
                val msg = UserChatBoard(user, text.text.toString())
                val arr = UserMessages()
                viewModel.chatViewModel.value?.add(msg)
                arr.messages = viewModel.chatViewModel.value!!
                viewModel.updateDocument(arr, USER_COLLECTION, userChannel)
                text.text?.clear()
            }
        }
    }
}