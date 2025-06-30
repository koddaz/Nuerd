package com.example.nuerd.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@IgnoreExtraProperties
data class User(
    val username: String = "",
    val email: String = "",
    val country: String = "",
    val id: String = ""
)

open class AuthViewModel: ViewModel() {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database.reference

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    open val authState: StateFlow<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    open fun checkAuthStatus() {
        viewModelScope.launch {
            _authState.value = if (auth.currentUser != null)
                AuthState.Authenticated
            else
                AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    open fun updatePassword(
        oldPassword: String,
        newPassword: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        val user = auth.currentUser
        if (user != null && user.email != null) {
            val credential = EmailAuthProvider.getCredential(user.email.toString(), oldPassword)
            user.reauthenticate(credential).addOnCompleteListener { reauthTask ->
                if (reauthTask.isSuccessful) {
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            onResult(true, null)
                        } else {
                            onResult(false, updateTask.exception?.message)
                        }
                    }
                } else {
                    onResult(false, reauthTask.exception?.message)
                }
            }
        } else {
            onResult(false, "User not logged in")
        }
    }

    open fun deleteAccount(onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        val user = auth.currentUser
        val uid = user?.uid
        if (uid != null) {
            database.child("Users").child(uid).removeValue()
                .addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        user.delete().addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                _authState.value = AuthState.Unauthenticated
                                onResult(true, null)
                            } else {
                                onResult(false, authTask.exception?.message)
                            }
                        }
                    } else {
                        onResult(false, dbTask.exception?.message)
                    }
                }
        } else {
            Log.e("Firebase", "User UID is null")
            onResult(false, "User UID is null")
        }
    }

    open fun databaseAdd(uid: String, username: String, email: String) {
        val newUser = User(
            username = username,
            email = email,
            id = uid
        )
        database.child("Users").child(uid).setValue(newUser)
    }

    open fun databaseGet(userId: String, onResult: (User?) -> Unit) {
        database.child("Users").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val user = snapshot.getValue(User::class.java)
                        onResult(user)
                    } else {
                        onResult(null)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error fetching user: ${error.message}")
                    onResult(null)
                }
            })
    }

    fun signUp(email: String, password: String, username: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = authResult.user?.uid
                if (uid != null) {
                    databaseAdd(uid, username, email)
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error("User ID is null")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    open fun signOut() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }
}

@IgnoreExtraProperties
sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}