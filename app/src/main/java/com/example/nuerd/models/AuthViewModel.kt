package com.example.nuerd.models

import android.util.Log
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@IgnoreExtraProperties
data class User(
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val country: String? = null,
)

data class Country(
    val name: Name,
    val cca2: String,
    val flags: Flags
)

data class Name(
    val common: String
)

data class Flags(
    val png: String
)

interface CountryRepository {
    @GET("v3.1/all")
    suspend fun getCountries(
        @Query("region") region: String? = null,
        @Query("q") query: String? = null
    ): List<Country>
}

class getCountriesViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://restcountries.com/") // Ensure the baseUrl ends with a '/'
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val countryApiService = retrofit.create(CountryRepository::class.java)

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries


    init {
        loadCountries()
    }

    fun loadCountries(region: String? = null, query: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val countriesList = countryApiService.getCountries(region, query)
                val flags = countriesList.map { it.flags.png }
                val sortedCountriesList = countriesList.sortedBy { it.name.common }
                _countries.postValue(sortedCountriesList)
                Log.d("Countries", "Countries loaded: $countriesList")
            } catch (e: Exception) {
                Log.e("getCountriesViewModel", "Error loading countries", e)
            }
        }
    }
}


class AuthViewModel(): ViewModel() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database.reference

    protected val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        _authState.value = if (auth.currentUser != null) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }

    fun signIn(email: String, password: String) {
        _authState.value = AuthState.Loading

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkAuthStatus()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Sign in failed")
                }
            }
    }

    fun highScore(highscore: Int) {
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null) {
            database.child("Users").child(uid).child("highScore").setValue(highscore)
        }
    }
    fun updateAccount(username: String, email: String, country: String) {
        val user = auth.currentUser
        if (user != null) {
            val userUpdates = hashMapOf<String, Any>(
                "username" to username,
                "email" to email,
                "country" to country
            )
            database.child("Users").child(user.uid).updateChildren(userUpdates)
        }
    }

    fun updatePassword(oldPassword: String, newPassword: String, onResult: (Boolean, String?) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
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

    fun deleteAccount() {
        val user = auth.currentUser
        val uid = user?.uid
        if (uid != null) {
            database.child("Users").child(uid).removeValue()
            auth.currentUser?.delete()
        } else {
            Log.e("Firebase", "User UID is null")
        }
    }
    fun databaseAdd(uid: String, username: String, email: String, country: String) {
        val newUser = User(
            username = username,
            email = email,
            country = country,
        )
        database.child("Users").child(uid).setValue(newUser) // Store by user UID
    }

    fun databaseGet(userId: String, onResult: (User?) -> Unit) {
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

    fun signUp(email: String, password: String, username: String, country: String) {
        _authState.value = AuthState.Loading

        if (email.isBlank() || password.isBlank() || username.isBlank() || country.isBlank()) {
            _authState.value = AuthState.Error("Fields cannot be empty")
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user?.uid

                    if (uid != null) {
                        databaseAdd(uid, username, email, country)
                    }
                    checkAuthStatus()
                } else {
                    val errorMessage = task.exception?.localizedMessage ?: "Unknown error"
                    _authState.value = AuthState.Error(errorMessage)
                }
            }
    }

    fun setUnauthenticated() {
        _authState.value = AuthState.Unauthenticated
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error (val message : String) : AuthState()
}