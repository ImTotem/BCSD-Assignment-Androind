package io.github.imtotem.bcsd_assignment.db

import androidx.room.*

@Dao
interface WordDao {
    @Query("SELECT * FROM word ORDER BY id DESC")
    fun getAll(): List<Word>

    @Query("SELECT * FROM word ORDER BY id DESC LIMIT 1")
    fun getLastWord(): Word?

    @Insert
    fun insert(word: Word)

    @Update
    fun update(word: Word)

    @Delete
    fun delete(word: Word)
}