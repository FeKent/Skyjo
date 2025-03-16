package com.fekent.skyjo.persistence


import android.util.Log
import com.fekent.skyjo.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.database.database

val database = Firebase.database(BuildConfig.FIREBASE_URL)
val myRef = database.getReference("test/Lenora")

fun sendMessage(){
    myRef.setValue("Hello, World")
    Log.d("FirebaseURL", "Firebase Database URL: ${BuildConfig.FIREBASE_URL}")

}

