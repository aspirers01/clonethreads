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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ProfileViewModel {

    val db=FirebaseDatabase.getInstance()
    val ref=db.getReference("threads")
     val userref=db.getReference("users")
    private val _threads= MutableLiveData(listOf<ThreadModel>())
    val threads: LiveData<List<ThreadModel>> = _threads
      private val _user= MutableLiveData(UserModel())
    val user: LiveData<UserModel> = _user
    private val _followerlist= MutableLiveData(listOf<String>())
    val followerlist: LiveData<List<String>> = _followerlist

    private val _followinglist= MutableLiveData(listOf<String>())
    val followinglist: LiveData<List<String>> = _followinglist



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
          userref.orderByChild("uid").equalTo(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (usersnapshot in snapshot.children) {
                    val user = usersnapshot.getValue(UserModel::class.java)
                    user?.let {
                        _user.postValue(it)
                    }
                }

            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                TODO("Not yet implemented")
            }

    })
    }

    // get instance of firestore
    val firestoredb=Firebase.firestore
    fun getfollower(uid:String){
        firestoredb.collection("followers").document(uid).addSnapshotListener { value, error ->
            if (error!=null){
                return@addSnapshotListener
            }
            val followerlist= value?.get("followersIds") as? List<String> ?: listOf()
            _followerlist.postValue(followerlist)
        }

    }
    fun getfollowing(uid:String){
        firestoredb.collection("following").document(uid).addSnapshotListener { value, error ->
            if (error!=null){
                return@addSnapshotListener
            }
            val followinglist= value?.get("followingIds") as? List<String> ?: listOf()
            _followinglist.postValue(followinglist)
        }

    }

    fun startfollow(uidofuser:String,currentuser:String){
        val ref=firestoredb.collection("following").document(currentuser)
        val followerref=firestoredb.collection("followers").document(uidofuser)
        ref.update("followingIds",FieldValue.arrayUnion(uidofuser))
        followerref.update("followersIds",FieldValue.arrayUnion(currentuser))
        firestoredb.collection("chats")
    }



}