package com.g02.yama.repository.dataAccess.dtos

import com.g02.yama.domain.Team

data class TeamDto(val name : String, val id : Int, val url : String, val privacy : String, val description: String?) {
    fun toTeam(): Team =
        Team(
                name,
                id,
                url,
                privacy,
                if (description.isNullOrBlank()) "Not Available" else description!!
        )

}