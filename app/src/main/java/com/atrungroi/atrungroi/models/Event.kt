package com.atrungroi.atrungroi.models

/**
 * Created by Admin on 11/18/2017.
 */
class Event {
    var idEvent: String? = ""
    var title: String? = ""
    var dateTimeStart: String? = ""
    var dateTimeEnd: String? = ""
    var timePost: String? = ""
    var content: String? = ""
    var imagesEvent: String = ""
    var idUser: String? =""
    var nameUser: String? = ""
    var checkQR: String? = ""

    constructor()

    constructor(idEvent: String?, title: String?, dateTimeStart: String?, dateTimeEnd: String?, timePost: String?, content: String?, imagesEvent: String, idUser: String?, nameUser: String?, checkQR: String?) {
        this.idEvent = idEvent
        this.title = title
        this.dateTimeStart = dateTimeStart
        this.dateTimeEnd = dateTimeEnd
        this.timePost = timePost
        this.content = content
        this.imagesEvent = imagesEvent
        this.idUser = idUser
        this.nameUser = nameUser
        this.checkQR = checkQR
    }


}