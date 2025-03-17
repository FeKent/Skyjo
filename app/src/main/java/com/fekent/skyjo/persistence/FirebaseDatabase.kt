package com.fekent.skyjo.persistence

import android.util.Log
import com.fekent.skyjo.BuildConfig
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

val database = Firebase.database(BuildConfig.FIREBASE_URL)
val myRef = database.getReference("test/Pongo")

fun sendMessage(){
    myRef.setValue("Hello, World")
    Log.d("FirebaseURL", "Firebase Database URL: ${BuildConfig.FIREBASE_URL}")
}

fun readMessage(onMessageReceived: (String) -> Unit){
    myRef.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val message = snapshot.getValue(String::class.java) ?: "No message found"
            onMessageReceived(message) // Update UI dynamically
            Log.d("FirebaseDB", "Message retrieved: $message")
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("FirebaseDB", "Failed to retrieve message", error.toException())
            onMessageReceived("Error fetching message")
        }
    })
}


//testing end to end communication with the least amount of code possible is called TRACER BULLETS
