package com.g02.yama.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import com.g02.yama.R
import com.g02.yama.viewModelProvider.memberViewModel
import com.g02.yama.viewModelProvider.userViewModel
import com.g02.yama.view_models.TeamMemberViewModel
import com.g02.yama.view_models.UserViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.app_bar_user_info.*
import kotlinx.android.synthetic.main.content_user_info.*
import kotlinx.android.synthetic.main.navigation_view.*

class UserInfoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var usrViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)

        usrViewModel = this.userViewModel()

        usrViewModel.init()

        usrViewModel.getUser().observe(this, Observer {
            if (it != null) {
                name.text = it.name
                login.text = it.login
                email.text = it.email
                followers.text = it.followers.toString()
                usrViewModel.setImagRequest(it.avatar_url)
                usrViewModel.getImage().observe(this, Observer {
                    if (it != null) {
                        photo.setImageBitmap(it)
                    }
                })
            }
        })

        UserChannel.setOnClickListener {
            if(internetConnected()) {
                val intent = Intent(this, UserChatActivity::class.java)
                intent.putExtra("Channel", login.text)
                intent.putExtra("User", login.text)
                startActivity(intent)
                finish()
            }
        }

        val toggle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun internetConnected(): Boolean {
        val net: ConnectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return net.activeNetworkInfo != null && net.activeNetworkInfo.isConnected
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user_info, menu)
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.userInfo -> {

            }
            R.id.teams -> {
                startActivity(Intent(this, TeamActivity::class.java))
                finish()
            }


            R.id.logout -> {
                val memberViewModel: TeamMemberViewModel
                memberViewModel = this.memberViewModel()
                memberViewModel.deleteSharedPreferences()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }



}
