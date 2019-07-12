package com.g02.yama.repository.dataAccess.dtos


class UserChatBoard(){

    var user: String = String()
    var msg : String = String()

    constructor(user: String, msg: String) : this() {
        this.user = user
        this.msg = msg
    }
    override fun toString(): String {
        return user + "\n" + msg
    }
}
