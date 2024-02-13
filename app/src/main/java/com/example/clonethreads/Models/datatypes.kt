package com.example.clonethreads.Models


data class UserData(
    var userId:String?="",
    var name:String?="",
    var email:String?="",
    var imageUrl:String?="",
){
    fun toMap()=mapOf(
        "userId" to userId,
        "name" to name,
        "email" to email,
        "imageUrl" to imageUrl
    )
}

data class ChatData(
    val chatId:String?="",
    val user1:ChatUser=ChatUser(),
    val user2:ChatUser=ChatUser()

)

data class ChatUser(
    val userId:String?="",
    val name:String?="",
    val imageUrl:String?=""
)

data class Message(
    val sendBy:String?="",
    val message:String?="",
    val timestamp:String?=""
)
