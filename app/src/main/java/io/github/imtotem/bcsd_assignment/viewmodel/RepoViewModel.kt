package io.github.imtotem.bcsd_assignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RepoViewModel: ViewModel() {
    val username = MutableLiveData("")

    fun setUsername(username: String) {
        this.username.value = username
    }
}