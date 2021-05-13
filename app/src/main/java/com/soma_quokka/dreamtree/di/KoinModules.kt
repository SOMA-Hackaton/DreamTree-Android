package com.soma_quokka.dreamtree.di

import com.soma_quokka.dreamtree.presentation.main.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MapViewModel()
    }
}