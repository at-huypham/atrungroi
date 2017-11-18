package com.atrungroi.atrungroi.models

/**
 * Created by huyphamna.
 */
class News {
    var title: String ?= ""
    var content: String ?= ""
    var image: Int ?= 0

    constructor()

    constructor(title: String?, content: String?, image: Int?) {
        this.title = title
        this.content = content
        this.image = image
    }

}
