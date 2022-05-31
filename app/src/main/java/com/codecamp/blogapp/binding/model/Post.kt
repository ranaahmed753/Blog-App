package com.codecamp.blogapp.binding.model

class Post{
     var name : String? = null
     var image : String? = null
     var post : String? = null
     var date : String? = null
     var email : String? = null
     var bio : String? = null
    var totalchild : Long? = null

    constructor(){}

    constructor(name : String?,image : String?,post : String?,date : String?,email : String?,bio : String?,totalchild : Long?){
        this.name = name
        this.image = image
        this.post = post
        this.date = date
        this.email = email
        this.bio = bio
        this.totalchild = totalchild
    }
}