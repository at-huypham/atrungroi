package com.atrungroi.atrungroi.models

/**
 * Created by Admin on 11/18/2017.
 */
class User {
    var idUser: String? = ""
    var userName: String? = ""
    var password: String? = ""
    var email: String? = ""
    var address: String? = ""
    var company: String? = ""
    var homeTown: String? = ""
    var hobby: String? = ""
    var age: Int? = 0
    var gender: String? = ""
    var avatar: String? = ""


    constructor()

    constructor(idUser: String?, userName: String?, password: String?, email: String?, address: String?, company: String?, homeTown: String?, hobby: String?, age: Int?, gender: String?, avatar: String?) {
        this.idUser = idUser
        this.userName = userName
        this.password = password
        this.email = email
        this.address = address
        this.company = company
        this.homeTown = homeTown
        this.hobby = hobby
        this.age = age
        this.gender = gender
        this.avatar = avatar
    }
}