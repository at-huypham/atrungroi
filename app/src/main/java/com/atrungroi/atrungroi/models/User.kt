package com.atrungroi.atrungroi.models

/**
 * Created by huyphamna on 18/11/2017.
 */
class User {
    var userName: String? = ""
    var password: String? = ""
    var email: String? = ""
    var address: String? = ""
    var company: String? = ""
    var age: Int? = 0

    constructor()

    constructor(userName: String?, password: String?, email: String?, address: String?, company: String?, age: Int?) {
        this.userName = userName
        this.password = password
        this.email = email
        this.address = address
        this.company = company
        this.age = age
    }
}
