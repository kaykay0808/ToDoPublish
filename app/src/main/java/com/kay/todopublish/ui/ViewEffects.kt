package com.kay.todopublish.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ViewEffects<T> {
    private val effects = Channel<T>()
    suspend fun collect(block: suspend (T) -> Unit) {
        effects.receiveAsFlow().collect { block(it) }
    }

    context(ViewModel)
    fun send(effect: T) {
        viewModelScope.launch {
            effects.send(effect)
        }
    }
}

// Applied some context receivers in gradle
// https://proandroiddev.com/applying-kotlin-context-receivers-5f2ad2ec4043
