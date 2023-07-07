package io.github.imtotem.bcsd_assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import io.github.imtotem.bcsd_assignment.db.Word
import io.github.imtotem.bcsd_assignment.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> by lazy { _word }

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList: LiveData<List<Word>> by lazy { _wordList }

    fun getAll() {
        viewModelScope.launch {
            _wordList.value = repository.getAll()
        }
    }

    fun getLatestWord() {
        viewModelScope.launch {
            setWord(repository.getLastWord())
        }
    }

    fun setWord(word: Word) {
        _word.value = word
    }

    fun insert(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun update(word: Word, currentList: List<Word>) {
        val job = viewModelScope.launch {
            repository.update(word)
        }
        if (job.isCompleted) {
            val items = _wordList.value as MutableList
            val idx = currentList.indexOfFirst { it.id == word.id }
            items[idx] = word
            _wordList.value = items
        }
    }

    fun delete(word: Word) {
        val job = viewModelScope.launch {
            repository.delete(word)
        }
        if (job.isCompleted) {
            val items = _wordList.value as MutableList
            items.remove(word)
            _wordList.value = items
        }
    }

    class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}