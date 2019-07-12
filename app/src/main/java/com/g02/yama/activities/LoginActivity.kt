package com.g02.yama.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.g02.yama.R
import com.g02.yama.viewModelProvider.loginViewModel
import com.g02.yama.viewModelProvider.sharedPref
import com.g02.yama.view_models.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {


    private lateinit var mainViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = this.loginViewModel()

        previousLogin()

        setClickListeners()
    }

    private fun setClickListeners() {
        next.setOnClickListener {
            setTextView()
        }

        reset.setOnClickListener {
            userID.text.clear()
            organizationID.text.clear()
            pat.text.clear()
        }
    }

    private fun setTextView() {
        var user = userID.text.toString()
        var organization = organizationID.text.toString()
        var token = pat.text.toString()
        if(user.equals("test")){
            user = "joao-francisco"
            organization = "isel-leic-pdm"
            token = "b8f06d0ae209dfc9b4110e73e8eae5917024d9ec"

        }
        if(user.equals("test2")){
            user = "theliso"
            organization = "isel-leic-pdm"
            token = "a7a04f1cb0140681beb0057b5540cd1d0a936594"

        }
        if(user.equals("test3")){
            user = "GAllen98"
            organization = "isel-leic-pdm"
            token = "cb9507c9a2a473a8c3de5e51ece606a98c0e5a82"

        }

        if (!emptyCredencials(user, token, organization)) {
            if (internetConnected()) {
                startRequests(user, token, organization)
            } else {
                Toast.makeText(
                        this,
                        resources.getString(R.string.connect_internet), Toast.LENGTH_LONG
                ).show()
            }
        }

    }


    private fun previousLogin(): Unit {
        val tokenSaved = application.sharedPref.getToken()
        if (!tokenSaved.contentEquals("not available")) {
            startActivity(Intent(this, UserInfoActivity::class.java))
            finish()
        }
    }

    private fun startRequests(user: String, token: String, organization: String) {
        mainViewModel.init(token, user, organization)
        mainViewModel.getUser().observe(this@LoginActivity, Observer {
            if (it != null && it.login == user) {
                mainViewModel.CanSave()
                mainViewModel.setPeriodicWork(token, organization)
                val intent = Intent(this, UserInfoActivity::class.java)
                startActivity(intent)
                finish()
            }
            reset.performClick()
        })
    }

    private fun internetConnected(): Boolean {
        val net: ConnectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return net.activeNetworkInfo != null && net.activeNetworkInfo.isConnected
    }

    fun emptyCredencials(user: String, token: String, organization: String): Boolean {
        if (user.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.insert_user_id),
                    Toast.LENGTH_SHORT).show()
            return true
        }
        if (organization.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.insert_organization_id),
                    Toast.LENGTH_SHORT)
                    .show()
            return true
        }
        if (token.isEmpty()) {
            Toast.makeText(this, resources.getString(R.string.insert_token),
                    Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }

}
