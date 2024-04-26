package com.neupanesushant.kastha.domain.managers

import com.google.firebase.database.FirebaseDatabase

object FirebaseManager {
    val firebaseDatabase : FirebaseDatabase get() = FirebaseDatabase.getInstance("https://kastha-4bbac-default-rtdb.asia-southeast1.firebasedatabase.app/")
}