package com.example.clonethreads.Viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonethreads.Models.ThreadModel
import com.example.clonethreads.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserThreadViewModel: ViewModel() {
    val db = FirebaseDatabase.getInstance()
    val threadref = db.getReference("threads")
    val uid = FirebaseAuth.getInstance()
    val userref = db.getReference("users")

    private val _threads = MutableLiveData<List<ThreadModel>>()
    val threads: LiveData<List<ThreadModel>> = _threads

    private val _userdata = MutableLiveData(UserModel())
    val userdata: LiveData<UserModel> = _userdata




 fun FetchUserdata(uid:String){

     userref.child(uid).addValueEventListener(object :com.google.firebase.database.ValueEventListener{
         override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
             val user=snapshot.getValue(UserModel::class.java)
              Log.d("UserThreadViewModel", "onDataChange: ${user?.username}")
                     _userdata.postValue(user)
         }

         override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
             TODO("Not yet implemented")
         }

     })

 }
    fun FetchThread(uid:String){

         threadref.addValueEventListener(object:com.google.firebase.database.ValueEventListener{
             override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                 val result= mutableListOf<ThreadModel>()
                 for(threadsnapshot in snapshot.children){
                     val thread=threadsnapshot.getValue(ThreadModel::class.java)
                     thread.let {
                         if (thread != null) {
                             if (thread.uid == uid) {
                                 result.add(0, it!!)
                             }
                         }
                     }
                 }
                 _threads.postValue(result)
             }

             override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                 TODO("Not yet implemented")
             }

         })

    }



}
