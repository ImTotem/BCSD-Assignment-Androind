package io.github.imtotem.bcsd_assignment.repository

import android.app.Application
import io.github.imtotem.bcsd_assignment.db.AppDatabase
import io.github.imtotem.bcsd_assignment.db.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WordRepositoryImpl(application: Application) : WordRepository {
    private val appDatabase = AppDatabase.getInstance(application)
    private val wordDao = appDatabase.wordDao()

    override suspend fun getAll(): List<Word> {
        val deferredAllWord = CoroutineScope(Dispatchers.IO).async {
            wordDao.getAll()
        }
        return deferredAllWord.await()
    }

    override suspend fun getLastWord(): Word {
        val deferredLatestWord = CoroutineScope(Dispatchers.IO).async {
            wordDao.getLastWord()!!
        }
        return deferredLatestWord.await()
    }

    override suspend fun insert(word: Word) {
        CoroutineScope(Dispatchers.IO).launch {
            wordDao.insert(word)
        }
    }

    override suspend fun update(word: Word) {
        CoroutineScope(Dispatchers.IO).launch {
            wordDao.update(word)
        }
    }

    override suspend fun delete(word: Word) {
        CoroutineScope(Dispatchers.IO).launch {
            wordDao.delete(word)
        }
    }
}