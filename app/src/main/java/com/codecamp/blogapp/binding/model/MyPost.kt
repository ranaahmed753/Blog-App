package com.codecamp.blogapp.binding.model

class MyPost {
    var name : String? = null
    var image : String? = null
    var post : String? = null
    var date : String? = null

    constructor(){}

    constructor(name : String?,image : String?,post : String?,date : String?){
        this.name = name
        this.image = image
        this.post = post
        this.date = date
    }
}