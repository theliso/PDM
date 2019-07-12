package com.g02.yama.repository.dataAccess.dtos

import com.g02.yama.domain.Member
import com.g02.yama.domain.TeamMember

data class TeamMemberDto(val members: Array<MembersDto>) {

    fun toTeamMember(): TeamMember {
        return TeamMember(
                Array<Member>(members.size) {
                    var elem = members.get(it)
                    Member(
                            elem.login,
                            elem.id,
                            elem.type,
                            elem.idTeam
                    )
                }
        )
    }


}