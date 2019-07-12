package com.g02.yama.repository.dataAccess.dtos

import com.g02.yama.domain.Organization

data class OrganizationDto(val teams: Array<TeamDto>) {

    fun toOrganization(): Organization =
            Organization(
                    Array(teams.size) { idx ->
                        teams.get(idx).toTeam()
                    }
            )


}