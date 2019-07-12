package com.g02.yama.repository.dataAccess.dtos

class MembersDto(
        val login: String,
        val id: String,
        val type: String,
        val idTeam: Int
) {


    override fun toString(): String {
        return "login: " + login + "\n" +
                "id: " + id + "\n" +
                "type: " + type + "\n" +
                "team:" + idTeam
    }

}