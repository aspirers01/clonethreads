package com.example.clonethreads.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE


object SharedPref {

    fun storedata(name:String,email:String,uid:String,image:String,bio:String,context: Context){
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        val editor = pref?.edit()
        editor?.putString("name",name)
        editor?.putString("email",email)
        editor?.putString("uid",uid)
        editor?.putString("image",image)
        editor?.putString("bio",bio)
        editor?.apply()

    }

    fun getname(context:Context):String{
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        return pref.getString("name","").toString()
    }

    fun getemail(context:Context):String{
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        return pref.getString("email","").toString()
    }
    fun image(context:Context):String{
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        return pref.getString("image","").toString()
    }
    fun getusername(context:Context):String{
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        return pref.getString("uid","").toString()
    }
    fun getbio(context:Context):String{
        val pref = context.getSharedPreferences("users",MODE_PRIVATE)
        return pref.getString("bio","").toString()
    }
}