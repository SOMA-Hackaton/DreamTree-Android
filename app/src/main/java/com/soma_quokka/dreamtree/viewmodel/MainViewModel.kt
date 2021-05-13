package com.soma_quokka.dreamtree.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

}