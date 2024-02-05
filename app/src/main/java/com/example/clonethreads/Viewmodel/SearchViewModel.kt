package com.example.clonethreads.Viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonethreads.Models.ThreadModel
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User

class SearchViewModel:ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val userref = db.getReference("users")
   private  val currentuser=FirebaseAuth.getInstance().currentUser?.uid
    private val _searchresult = MutableLiveData<List< UserModel>>()
 val searchresult: LiveData<List< UserModel>> = _searchresult
     init{
            fetchuser(onResult = {
                _searchresult.value=it
            })
     }

    private fun fetchuser(onResult: (List<UserModel>)->Unit) {
         userref.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val result = mutableListOf<UserModel>()
                for (usersnapshot in snapshot.children) {
                    val user = usersnapshot.getValue(UserModel::class.java)
                    user?.let {
                        if(it.uid!=currentuser){
                            result.add(it)
                        }
                        if (result.size == snapshot.children.count()-1) {
                            onResult(result)
                        }
                    }
                }
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                TODO()
            }

        })


    }

}