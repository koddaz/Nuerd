package com.example.nuerd.models

import android.util.Log
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
import kotlin.collections.getValue

data class HighScoreEntry(
    val userId: String = "",
    val username: String = "",
    val score: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val country: String = ""
)

class HighScoreViewModel: ViewModel() {
    private val db = Firebase.database.reference
    private val auth = FirebaseAuth.getInstance()

    private val _currentHighScore = MutableStateFlow<Int>(0)
    private val _userHighScore = MutableStateFlow<Int>(0)
    private val _allHighScores = MutableStateFlow<List<HighScoreEntry>>(emptyList())

    val currentHighScore: StateFlow<Int> = _currentHighScore.asStateFlow()
    val userHighScore: StateFlow<Int> = _userHighScore.asStateFlow()
    val allHighScores: StateFlow<List<HighScoreEntry>> = _allHighScores.asStateFlow()

    init {
        fetchUserHighScore()
        fetchAllHighScores()
    }

    fun saveHighScore(score: Int) {
        val user = auth.currentUser ?: return
        Log.d("HighScoreViewModel", "Attempting to save score: $score for user: ${user.uid}")

        db.child("Users").child(user.uid).child("highScore")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val currentScore = snapshot.getValue(Int::class.java)
                    Log.d("HighScoreViewModel", "Current score: $currentScore, New score: $score")

                    if (currentScore == null || score > currentScore) {
                        // Save to user's personal high score
                        db.child("Users").child(user.uid).child("highScore").setValue(score)
                            .addOnSuccessListener {
                                Log.d("HighScoreViewModel", "Personal high score updated successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("HighScoreViewModel", "Failed to update personal high score", e)
                            }

                        _userHighScore.value = score

                        // Always save to global high scores
                        saveToGlobalHighScores(user.uid, score)
                    } else {
                        Log.d("HighScoreViewModel", "Score not high enough to update")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HighScoreViewModel", "Error checking current score", error.toException())
                }
            })
    }

    private fun saveToGlobalHighScores(userId: String, score: Int) {
        db.child("Users").child(userId).child("username")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(usernameSnapshot: DataSnapshot) {
                    val username = usernameSnapshot.getValue(String::class.java) ?: "Unknown"

                    // Get country for complete high score entry
                    db.child("Users").child(userId).child("country")
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(countrySnapshot: DataSnapshot) {
                                val country = countrySnapshot.getValue(String::class.java) ?: ""

                                val highScoreEntry = HighScoreEntry(
                                    userId = userId,
                                    username = username,
                                    score = score,
                                    timestamp = System.currentTimeMillis(),
                                    country = country
                                )

                                Log.d("HighScoreViewModel", "Saving entry to GlobalHighScores: $highScoreEntry")

                                db.child("GlobalHighScores").child(userId).setValue(highScoreEntry)
                                    .addOnSuccessListener {
                                        Log.d("HighScoreViewModel", "Global high score saved successfully")
                                        fetchAllHighScores()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("HighScoreViewModel", "Failed to save global high score: ${e.message}", e)
                                    }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.e("HighScoreViewModel", "Error getting country", error.toException())
                            }
                        })
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HighScoreViewModel", "Error getting username", error.toException())
                }
            })
    }

    fun fetchAllHighScores() {
        Log.d("HighScoreViewModel", "Fetching all high scores")
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
                    Log.d("HighScoreViewModel", "Fetched ${highScores.size} high scores")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HighScoreViewModel", "Error fetching all high scores", error.toException())
                }
            })
    }

    fun fetchUserHighScore() {
        val user = auth.currentUser ?: return
        Log.d("HighScoreViewModel", "Fetching user high score for ${user.uid}")

        db.child("Users").child(user.uid).child("highScore")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val score = snapshot.getValue(Int::class.java) ?: 0
                    _userHighScore.value = score
                    Log.d("HighScoreViewModel", "User high score: $score")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("HighScoreViewModel", "Error fetching user high score", error.toException())
                }
            })
    }

    fun getTopHighScores(limit: Int): List<HighScoreEntry> {
        return allHighScores.value.take(limit)
    }
}


