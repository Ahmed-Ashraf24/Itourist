package com.example.itouristui

class Message(var sender:String, var message:String,val messageType:Int, val date :String){
    companion object {
        const val MY_MESSAGE = 0
        const val Others_MESSAGE = 1
        const val rating_message=2
    }

}
