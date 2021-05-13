package com.soma_quokka.dreamtree.presentation.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.soma_quokka.dreamtree.presentation.base.BaseViewModel

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

}