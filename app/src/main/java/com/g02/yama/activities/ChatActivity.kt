package com.g02.yama.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
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


class ChatActivity : AppCompatActivity() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var viewAdapter: Adapter<*>
    private lateinit var linearLayoutManager: LinearLayoutManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        viewModel = this.chatViewModel()

        val teamChat = intent?.extras?.getString("id")!!
        val org = intent?.extras?.getString("org")!!
        val user = intent?.extras?.getString("user")!!

        val document = getTeamChatDocument(teamChat, org)

        viewModel.init(org, teamChat)

        viewAdapter = ChatRecyclerView(this, viewModel.chatViewModel)
        linearLayoutManager = LinearLayoutManager(this)

        viewModel.getMessages().observeForever{
            this.chatBoard.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = viewAdapter
            }
        }

        viewModel.updateMessages(org, teamChat).observeForever{
            if(it.isNotEmpty()){
                viewModel.chatViewModel.value = it
            }
        }
        setClickListeners(document, user, teamChat, org)
    }


    private fun getTeamChatDocument(teamChat: String, org: String)
            : DocumentReference {
        return FirebaseFirestore
                .getInstance()
                .collection(org)
                .document(teamChat)
    }

    private fun setClickListeners(
            document: DocumentReference,
            user: String,
            teamChat: String,
            org: String
    ) {

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
                viewModel.updateDocument(arr, org, teamChat)
                text.text?.clear()
            }
        }
    }

}
