package com.example.nuerd.models

import androidx.lifecycle.ViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DataViewModel : ViewModel() {

    val db = Firebase.database.reference

}