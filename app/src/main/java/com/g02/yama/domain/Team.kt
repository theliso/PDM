package com.g02.yama.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Team(
        @ColumnInfo(name = "name") val name: String,
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "url") val url: String,
        @ColumnInfo(name = "privacy") val privacy: String,
        @ColumnInfo(name = "description") val description: String
) {

    override fun toString(): String {
        return "name: " + name + "\n" +
                "id: " + id + "\n" +
                "url: " + url + "\n" +
                "privacy: " + privacy + "\n" +
                "description: " + description
    }

}