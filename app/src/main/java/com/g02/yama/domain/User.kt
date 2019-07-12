package com.g02.yama.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class User (
        @PrimaryKey var login: String,
        @ColumnInfo(name = "name") var name: String?,
        @ColumnInfo(name = "email") var email: String?,
        @ColumnInfo(name = "followers") var followers: Int,
        @ColumnInfo(name = "avatar") var avatar_url: String
) {
}