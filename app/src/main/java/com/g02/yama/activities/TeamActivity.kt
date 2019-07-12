package com.g02.yama.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.g02.yama.R
import com.g02.yama.recycler_views.TeamsRecyclerView
import com.g02.yama.viewModelProvider.memberViewModel
import com.g02.yama.viewModelProvider.teamViewModel
import com.g02.yama.view_models.TeamMemberViewModel
import com.g02.yama.view_models.TeamViewModel
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : AppCompatActivity() {

    private lateinit var teamsViewModel: TeamViewModel

    private lateinit var viewAdapter: Adapter<*>
    private lateinit var linearLayoutManager: LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        teamsViewModel = this.teamViewModel()
        teamsViewModel.init()
        linearLayoutManager = LinearLayoutManager(this)
        viewAdapter = TeamsRecyclerView( this, teamsViewModel.teamViewModel){
            clickListener(it)
        }
        teamsViewModel.getTeams().observe(this, Observer {
            this.recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = linearLayoutManager
                adapter = viewAdapter
            }
        })

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
    }

    fun clickListener(id: Int){
        startActivity(
                Intent(this, TeamMemberActivity::class.java)
                        .apply {
                            putExtra("id", id.toString())
                            putExtra("org", teamsViewModel.getOrganization())
                            putExtra("user", teamsViewModel.getLogin())
                        }
        )
        finish()
    }

}
