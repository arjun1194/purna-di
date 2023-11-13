package com.arjun1194.purnadiexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyViewModel(
    private val myRepository: MyRepository
) : ViewModel() {

    val state = MutableStateFlow<String?>(null)

    fun sayHelloToActivity() {
        viewModelScope.launch {
            val message = myRepository.message
            state.emit(message)
        }
    }
}