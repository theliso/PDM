package com.g02.yama.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Member(
        @PrimaryKey val login: String,
        @ColumnInfo(name = "id") val id: String,
        @ColumnInfo(name = "type") val type: String,
        @ColumnInfo(name = "idTeam") val idTeam : Int
){

    override fun toString(): String {
        return "login: " + login + "\n" +
                "id: " + id + "\n" +
                "type: " + type
    }
}