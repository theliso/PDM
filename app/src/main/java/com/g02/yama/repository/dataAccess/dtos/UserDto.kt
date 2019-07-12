package com.g02.yama.repository.dataAccess.dtos

import com.g02.yama.domain.User

data class UserDto(
        val login: String,
        val name: String?,
        val email: String?,
        val followers: Int,
        var avatar_url: String
) {
    fun toUser(): User = User(login, name, email, followers, avatar_url)


}