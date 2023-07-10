package io.github.imtotem.bcsd_assignment.repository

import io.github.imtotem.bcsd_assignment.db.Word

interface WordRepository {
    suspend fun getAll(): List<Word>
    suspend fun getLastWord(): Word
    suspend fun insert(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
}