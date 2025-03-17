package com.example.nuerd.models

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HighScoreEntry(
    val userId: String = "",
    val username: String = "",
    val score: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

class HighScoreViewModel: ViewModel() {
    private val db = Firebase.database.reference
    private val auth = FirebaseAuth.getInstance()
    // private val userHighScores = db.database.reference.get()

    // private val _topScores = MutableStateFlow<List>()
    private val _currentHighScore = MutableStateFlow<Int>(0)
    private val _userHighScore = MutableStateFlow<Int>(0)
    private val _allHighScores = MutableStateFlow<List<HighScoreEntry>>(emptyList())

    val currentHighScore: StateFlow<Int> = _currentHighScore.asStateFlow()
    val userHighScore: StateFlow<Int> = _currentHighScore.asStateFlow()
    val allHighScores: StateFlow<List<HighScoreEntry>> = _allHighScores.asStateFlow()

    init {
        fetchUserHighScore()
        fetchAllHighScores()
    }

    fun saveHighScore(score: Int) {
        val user = auth.currentUser ?: return

        db.child("Users").child(user.uid).child("highScore")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentScore = snapshot.getValue(Int::class.java) ?: 0
                    if (score > currentScore) {

                        db.child("Users").child(user.uid).child("highScore").setValue(score)
                        _userHighScore.value = score

                        db.child("Users").child(user.uid).child("username")
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(usernameSnapshot: DataSnapshot) {
                                    val username = usernameSnapshot.getValue(String:: class.java) ?: "Unknown"

                                    val highScoreEntry = HighScoreEntry(
                                        userId = user.uid,
                                        username = username,
                                        score = score,
                                        timestamp = System.currentTimeMillis()
                                    )

                                    db.child("GlobalHighScores").child(user.uid).setValue(highScoreEntry)
                                    fetchAllHighScores()
                                }
                                override fun onCancelled(error: DatabaseError) {
                                    // Handle error
                                }
                            })
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // ERROR!!
                }
            })
    }


    fun fetchAllHighScores() {
        db.child("GlobalHighScores").orderByChild("score").limitToLast(10)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val highScores = mutableListOf<HighScoreEntry>()

                    for (scoreSnapshot in snapshot.children) {
                        val entry = scoreSnapshot.getValue(HighScoreEntry::class.java)
                        if (entry != null) {
                            highScores.add(entry)
                        }
                    }

                    // Sort by score (highest first)
                    _allHighScores.value = highScores.sortedByDescending { it.score }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
    }
    fun fetchUserHighScore() {

        val user = auth.currentUser ?: return

        db.child("Users").child(user.uid).child("highScore")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val score = snapshot.getValue(Int::class.java) ?: 0
                    _userHighScore.value = score
                }

                override fun onCancelled(error: DatabaseError) {
                    // ERROR
                }
            })
    }
    fun getTopHighScores(limit: Int): List<HighScoreEntry> {
        return allHighScores.value.take(limit)
    }
}


