package com.soma_quokka.dreamtree.presentation.map.view

import android.os.Bundle
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.databinding.ActivityMapBinding
import com.soma_quokka.dreamtree.presentation.base.BaseActivity
import com.soma_quokka.dreamtree.presentation.map.viewmodel.MapViewModel

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(R.layout.activity_map) {
    override val viewModel: MapViewModel
        get() = TODO("Not yet implemented")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}