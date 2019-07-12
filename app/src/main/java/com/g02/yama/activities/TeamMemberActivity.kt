package com.g02.yama.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.g02.yama.R
import com.g02.yama.recycler_views.TeamMembersRecycleView
import com.g02.yama.viewModelProvider.memberViewModel
import com.g02.yama.view_models.TeamMemberViewModel
import kotlinx.android.synthetic.main.activity_team_membersctivity.*

class TeamMemberActivity : AppCompatActivity() {

    private lateinit var memberViewModel: TeamMemberViewModel
    private lateinit var viewAdapter: Adapter<*>
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_membersctivity)

        memberViewModel = this.memberViewModel()

        val id : String? = intent?.extras?.getString("id")
        val org = intent?.extras?.getString("org")
        val user = intent?.extras?.getString("user")!!
        memberViewModel.init(id!!)

        linearLayoutManager = LinearLayoutManager(this)

        viewAdapter = TeamMembersRecycleView(this, memberViewModel.members){
            clickListener(it, user)
        }

        memberViewModel.getTeamMembers().observe(this, Observer {
            this.membersRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = viewAdapter
            }
        })
        setClickListeners(id!!, org, user)
    }

    private fun clickListener(it: String, user: String){
        if(internetConnected()) {
            Toast.makeText(this,"Hey! $it",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserChatActivity::class.java)
            intent.putExtra("Channel", it)
            intent.putExtra("User", user)
            startActivity(intent)
            finish()
        } else{
            Toast.makeText(this, resources.getString(R.string.connect_internet),
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun setClickListeners(id: String, org: String?, user: String) {
        backButton.setOnClickListener {
            deleteCache()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        teamsButton.setOnClickListener {
            startActivity(Intent(this, TeamActivity::class.java))
            finish()
        }

        infoButton.setOnClickListener {
            startActivity(Intent(this, UserInfoActivity::class.java))
            finish()
        }

        chatButton.setOnClickListener {
            if(internetConnected()) {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("org", org)
                intent.putExtra("user", user)
                startActivity(intent)
                finish()
            } else{
                Toast.makeText(this, resources.getString(R.string.connect_internet),
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun internetConnected(): Boolean {
        val net: ConnectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return net.activeNetworkInfo != null && net.activeNetworkInfo.isConnected
    }

    private fun deleteCache() {
        memberViewModel.getAllUsers().observe(this, Observer {
            memberViewModel.deleteUsers(it)
        })
        memberViewModel.getAllMembers().observe(this, Observer {
            memberViewModel.deleteMembers(it)
        })
        memberViewModel.getAllTeams().observe(this, Observer {
            memberViewModel.deleteTeams(it)
        })
        memberViewModel.deleteSharedPreferences()
    }
}
