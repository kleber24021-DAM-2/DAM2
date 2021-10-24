package com.example.crudfromjson.domain.ownmodels

import java.time.LocalDateTime

class Hero (name:String,
            description:String,
            dateModified:LocalDateTime,
            image:String,
            comics:MutableList<OwnComic>){
}