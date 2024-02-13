package com.example.clonethreads.Viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.clonethreads.Models.UserData
import com.example.clonethreads.Models.UserModel
import com.example.clonethreads.utils.SharedPref
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.util.UUID

class AuthViewmodel : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance()
    val userref = db.getReference("users")
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val sotrageRef = Firebase.storage.reference
    private val imageRef = sotrageRef.child("users/${UUID.randomUUID()}.jpg")

    init {
        _firebaseUser.value = auth.currentUser
        _isloading.value=false
    }

    fun login(email: String, password: String, context: Context) {
        _isloading.value = true
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                getdata(auth.currentUser?.uid!!, context)

            } else {
                _error.value = it.exception?.message
                _isloading.value=false
            }
        }
    }

    private fun getdata(uid: String, context: Context) {
        userref.child(uid).addListenerForSingleValueEvent(object :
            com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                SharedPref.storedata(
                    user!!.username,
                    user!!.email,
                    user!!.uid,
                    user!!.image,
                    user.bio,
                    context
                )
            }

            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                _isloading.value=false
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun signout() {
        auth.signOut()
        _firebaseUser.postValue(null)

    }

    fun Register(
        email: String,
        password: String,
        username: String,
        cnpassword: String,
        imageUri: Uri,
        context: Context
    ) {

        if (password != cnpassword) {
            _error.value = "passwords do not match"
            _isloading.value = false;
            return
        }
        Log.d("register", "Register: we are in register function")

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Log.d("register", "this got succes")
            _firebaseUser.postValue(auth.currentUser)
            Log.d("register", "calling saveimage")
            saveimage(email, password, username, auth.currentUser?.uid!!, imageUri, context)
        }.addOnFailureListener {
            _error.value = it.message
            _isloading.value = false;
            Toast.makeText(context, "something went wrong registration failed", Toast.LENGTH_SHORT)
                .show()
            Log.d("register this", "${_error.value}")
        }

    }

    private fun saveimage(
        email: String,
        password: String,
        username: String,
        uid: String,
        imageUri: Uri,
        context: Context
    ) {

        Log.d("register", "called saveimage")
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {

                Log.d("register", "calling data")
                savedata(email, password, username, uid, it.toString(), "  ", context)
            }.addOnFailureListener(
                object : OnFailureListener {

                    override fun onFailure(p0: Exception) {
                        _error.value = p0.message
                        _isloading.value = false;
                    }
                }
            )
        }
    }

    private fun savedata(
        email: String,
        password: String,
        username: String,
        uid: String,
        toString: String,
        s: String,
        context: Context
    ) {
        val userdatafirestore=UserData(
            userId = uid,
            name = username,
            email = email,
            imageUrl = toString
        )

        val firestoreDb=Firebase.firestore
        val followersref=firestoreDb.collection("followers").document(uid)
        val followingref=firestoreDb.collection("following").document(uid)
        val userreffire=firestoreDb.collection("users").document(uid).set(userdatafirestore)


    followingref.set(mapOf("followingIds" to listOf<String>()))
        followersref.set(mapOf("followersIds" to listOf<String>()))




        val user = UserModel(username, email, password, toString, uid, s)
        userref.child(uid).setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("register", "savedata: we are in savedata function")
                _firebaseUser.postValue(auth.currentUser)
                Log.d("register", "${_firebaseUser.value}")
                SharedPref.storedata(username, email, uid, toString, s, context)
                _isloading.value=false
            } else {
                _error.value = it.exception?.message
                _isloading.value = false;
            }
        }
    }




}