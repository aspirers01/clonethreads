package com.example.clonethreads.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonethreads.Models.ThreadModel
import com.example.clonethreads.Models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel:ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val threadref = db.getReference("threads")

   private val _threadNuser = MutableLiveData<List<Pair<ThreadModel, UserModel>>>()
    val threadandusers: LiveData<List<Pair<ThreadModel, UserModel>>> = _threadNuser


    init {
        FetchThreadanduser {
            _threadNuser.value = it
        }
    }
//to fetch data from firebase
    private  fun FetchThreadanduser(onResult: (List<Pair<ThreadModel,UserModel>>) -> Unit){
        threadref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
              val result= mutableListOf<Pair<ThreadModel,UserModel>>()
                for(threadsnapshot in snapshot.children){
                    val thread=threadsnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchuser(it!!){
                            user->
                            result.add(0,it to user)
                             if(result.size==snapshot.children.count()){
                                 onResult(result)
                             }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    // to fetch user from thread
    fun fetchuser(Thread:ThreadModel,onResult: (UserModel) -> Unit){
         //ToDo (if not work change users to threads in below line )
           db.getReference("users").child(Thread.uid).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user=snapshot.getValue(UserModel::class.java)
                    if(user!=null){
                        onResult(user)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO()
                }

            })

    }








}