package com.soma_quokka.dreamtree.presentation.main.view

import android.os.Bundle
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.databinding.ActivityMapBinding
import com.soma_quokka.dreamtree.presentation.base.BaseActivity
import com.soma_quokka.dreamtree.presentation.main.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(R.layout.activity_map) {
    override val viewModel: MapViewModel by viewModel()

    private val mapViewFragment = MapViewFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.fragment_map, mapViewFragment).commit()

    }
}