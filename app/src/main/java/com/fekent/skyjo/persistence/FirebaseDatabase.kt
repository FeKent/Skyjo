package com.fekent.skyjo.persistence


import com.google.firebase.Firebase
import com.google.firebase.database.database

val database = Firebase.database("placeholder")
val myRef = database.getReference("test/Snippy")

fun SendMessage(){
    myRef.setValue("Hello, World")
}

