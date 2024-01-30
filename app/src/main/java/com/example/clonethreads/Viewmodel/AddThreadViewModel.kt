package com.example.clonethreads.Viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonethreads.Models.ThreadModel
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.utils.SharedPref
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import java.util.UUID

class AddThreadViewModel:ViewModel(){

    val db = FirebaseDatabase.getInstance()
    val userref = db.getReference("threads")



    private val _isposted = MutableLiveData<Boolean>()
    val isposted: LiveData<Boolean> = _isposted
    private val sotrageRef = Firebase.storage.reference
    private val imageRef = sotrageRef.child("threads/${UUID.randomUUID()}.jpg")



 fun saveimage(
        thread: String,
        uid: String,
        imageUri: Uri
    ) {


        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
               savedata(thread,uid,it.toString())

            }
        }
    }

fun savedata(thread: String, uid: String, toString: String){
        val threadmodel = ThreadModel(thread, uid, toString, System.currentTimeMillis().toString())
        userref.child(userref.push().key!!).setValue(threadmodel).addOnCompleteListener {
            if (it.isSuccessful) {
                _isposted.postValue(true)
            } else {
                _isposted.postValue(false)
            }
        }
    }


}