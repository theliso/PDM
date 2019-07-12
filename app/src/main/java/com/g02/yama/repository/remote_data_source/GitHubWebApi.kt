package com.g02.yama.repository.remote_data_source

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.g02.yama.repository.dataAccess.dtos.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Integer.parseInt

object GitHubWebApi {

    val tag = GitHubWebApi::class.java.simpleName

    val urlUser = "https://api.github.com/user"


    private lateinit var queue: RequestQueue

    fun init(ctx: Context) {
        queue = Volley.newRequestQueue(ctx)
    }

    private fun request(method: Int, url: String, headers: Map<String, String>,
                        cb: (String) -> Unit) {
        val req = HeadersStringRequest(method, url, headers, onResponse(cb), onError())
        queue.add(req)
    }

    fun getUser(headers: Map<String, String> = mapOf(), cb: (UserDto) -> Unit) {
        request(Request.Method.GET, urlUser, headers) {
            val json = JSONObject(it)
            cb(UserDto(
                    json.getString("login"),
                    json.getString("name"),
                    json.getString("email"),
                    json.getInt("followers"),
                    json.getString("avatar_url")
            ))
        }
    }

    private fun onError(): Response.ErrorListener = Response.ErrorListener {
        err -> Log.i(tag, err.message)
    }

    private fun onResponse(cb: (String) -> Unit): Response.Listener<String> = Response.Listener {
        str -> cb(str)
    }

    fun getTeams(OrgId: String, headers: Map<String, String>, cb: (OrganizationDto) -> Unit) {
        val urlOrganization = "https://api.github.com/orgs/$OrgId/teams"
        request(Request.Method.GET, urlOrganization, headers) {
            val json = JSONArray(it)
            var array = Array<TeamDto>(json.length()) { idx ->
                var elem = json.getJSONObject(idx)
                TeamDto(
                        elem.getString("name"),
                        elem.getInt("id"),
                        elem.getString("url"),
                        elem.getString("privacy"),
                        elem.getString("description")
                )
            }
            cb(OrganizationDto(array))
        }

    }

    fun getTeamMembers(id: String, headers: Map<String, String>,
                       cb: (TeamMemberDto) -> Unit) {
        val urlMembers = "https://api.github.com/teams/$id/members"
        request(Request.Method.GET, urlMembers, headers) { it ->
            val json = JSONArray(it)
            var array = Array<MembersDto>(json.length()) {
                var elem = json.getJSONObject(it)
                MembersDto(
                        elem.getString("login"),
                        elem.getString("id"),
                        elem.getString("type"),
                        parseInt(id)
                )
            }
            cb(TeamMemberDto(array))
        }

    }

    fun getImage(url: String, headers: Map<String, String>, cb: (Bitmap) -> Unit) {
        var reqImg = ImageRequest(url, imgOnResponse(cb),
                0, 0, ImageView.ScaleType.CENTER_CROP, null,
                onError())
        queue.add(reqImg)
    }

    fun imgOnResponse(cb: (Bitmap) -> Unit) : Response.Listener<Bitmap> = Response.Listener {
        cb(it)
    }

    class HeadersStringRequest(method: Int, url: String, val hdrs: Map<String, String>,
                               listener: Response.Listener<String>,
                               errorListener: Response.ErrorListener) : StringRequest(method, url,
            listener, errorListener) {
        override fun getHeaders(): MutableMap<String, String> = hdrs.toMutableMap()
    }

}