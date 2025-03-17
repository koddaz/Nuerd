package com.example.nuerd.models

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase



@Entity(tableName = "difTable")
data class Difficulty(
    @PrimaryKey val id: Int = 0,
    val value: Int,
)

@Entity(tableName = "themeTable")
data class Theme(
    @PrimaryKey val id: Int = 0,
    val theme: String = "Green"
)

@Dao
interface SettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(difficulty: Difficulty)

    @Query("SELECT * FROM difTable WHERE id = 0 LIMIT 1")
    suspend fun getDifficulty(): Difficulty?

    @Query("UPDATE themeTable SET theme = :theme WHERE id = 0")
    suspend fun updateTheme(theme: String)

    @Query("SELECT theme FROM themeTable WHERE id = 0")
    suspend fun getThemeValue(): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheme(theme: Theme)

    @Query("SELECT * FROM themeTable WHERE id = 0 LIMIT 1")
    suspend fun getTheme(): Theme?
}

@Database(entities = [Difficulty::class, Theme::class], version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migration from version 1 to 2: create themeTable
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `themeTable` (`id` INTEGER NOT NULL, `theme` TEXT NOT NULL, PRIMARY KEY(`id`))")
            }
        }

        // Migration from version 2 to 3: Populate default theme
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("INSERT OR REPLACE INTO themeTable (id, theme) VALUES (0, 'Green')")
            }
        }

        // Callback to pre-populate data for fresh installs
        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // This runs when database is created for the first time
                db.execSQL("INSERT OR REPLACE INTO themeTable (id, theme) VALUES (0, 'Green')")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .addCallback(roomCallback) // Add the callback for fresh installs
                    .fallbackToDestructiveMigration() // Last resort if migrations fail
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class GameViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewModel(application, highScoreViewModel = HighScoreViewModel()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ThemeViewModelFactory(
    private val settingsDao: SettingsDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(settingsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}