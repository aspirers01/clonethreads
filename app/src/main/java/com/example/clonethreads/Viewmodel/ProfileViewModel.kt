package com.example.clonethreads.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clonethreads.Models.ThreadModel
import com.example.clonethreads.Models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileViewModel {

    val db=FirebaseDatabase.getInstance()
    val ref=db.getReference("threads")

    private val _threads= MutableLiveData(listOf<ThreadModel>())
    val threads: LiveData<List<ThreadModel>> = _threads
      private val _user= MutableLiveData(UserModel())
    val user: LiveData<UserModel> = _user



    fun getThreads(uid:String?){
        ref.orderByChild("uid").equalTo(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val result = mutableListOf<ThreadModel>()
                for (threadsnapshot in snapshot.children) {
                    val thread = threadsnapshot.getValue(ThreadModel::class.java)
                    thread?.let {
                        result.add(it)
                    }
                }
                _threads.postValue(result)
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

    fun getuser(uid:String){

    }



}