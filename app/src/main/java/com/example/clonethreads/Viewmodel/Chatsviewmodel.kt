package com.example.clonethreads.Viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonethreads.Models.ChatData
import com.example.clonethreads.Models.Message
import com.example.clonethreads.Models.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.Calendar

class Chatsviewmodel:ViewModel() {
    val chats = mutableStateOf<List<ChatData>>(listOf())
    private val userId=Firebase.auth.currentUser?.uid
     val auth=FirebaseAuth.getInstance()
    var db: FirebaseFirestore = Firebase.firestore
    var storage: FirebaseStorage = Firebase.storage

    var inProcessChats = mutableStateOf(false)
    val chatMessages = mutableStateOf<List<Message>>(listOf())
    val inProgressChatMessage = mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null


    init {
        getChats()
        Log.d("Chatsviewmodel", "init:${chats.value} ")
    }


   private fun getChats(){
        val db=Firebase.firestore
        db.collection("chats").where(
            Filter.or(
                Filter.equalTo("user1.userId", userId),
                Filter.equalTo("user2.userId", userId)
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
               Log.d("Chatsviewmodel", "getChats: ${error.message}")
            }
            if (value != null) {
                chats.value = value.documents.mapNotNull { it.toObject<ChatData>() }

            }

            }
        }

    fun populateMessages(chatId: String) {

        currentChatMessageListener = db.collection("chats").document(chatId).collection("message")
            .addSnapshotListener { value, error ->
                if (error != null) {
                  Log.d("Chatsviewmodel", "populateMessages: ${error.message}")
                }
                if (value != null) {
                    chatMessages.value = value.documents.mapNotNull {
                        it.toObject<Message>()
                    }.sortedBy { it.timestamp }
                    inProgressChatMessage.value = false
                }
            }
    }
    fun depopulateMessages() {
        chatMessages.value = listOf()
        currentChatMessageListener?.remove()
    }

    fun populateChats() {
        inProcessChats.value = true
        db.collection("chats").where(
            Filter.or(
                Filter.equalTo("user1.userId", userId),
                Filter.equalTo("user2.userId", userId)
            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
               Log.d("Chatsviewmodel", "populateChats: ${error.message}")
            }
            if (value != null) {
                chats.value = value.documents.mapNotNull { it.toObject<ChatData>() }
                inProcessChats.value = false
            }
        }
    }
    fun onSendReply(ChatID: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val msg = com.example.clonethreads.Models.Message(userId, message, time)
        db.collection("chats").document(ChatID).collection("message").document().set(msg)
    }


}


