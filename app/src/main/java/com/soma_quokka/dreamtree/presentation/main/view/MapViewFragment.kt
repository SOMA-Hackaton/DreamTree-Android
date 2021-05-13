package com.soma_quokka.dreamtree.presentation.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.data.model.Store
import com.soma_quokka.dreamtree.presentation.main.MapTypeConstant
import java.util.*

class MapViewFragment : Fragment(), OnMapReadyCallback {

    val stores = listOf(Store("임시1","일식", 37.5601, 126.9001),Store("임시1", "중식",37.5602, 126.9002))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fragment_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.fragment_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
        }

        return inflater.inflate(R.layout.activity_map, container, false)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val storeType = MapTypeConstant
        val cameraPosition = CameraPosition(LatLng(37.56, 126.90), 17.0)
        naverMap.cameraPosition = cameraPosition

        naverMap.uiSettings.isCompassEnabled = false

        val marker = Marker()
        marker.icon = OverlayImage.fromResource(R.drawable.ic_baseline_place_24)
        marker.position = LatLng(37.56, 126.90)
        marker.map = naverMap

        for (store in stores) {
            val m = Marker()
            when (store.type) {

            }
            m.icon = OverlayImage.fromResource(R.drawable.ic_pizza)
            m.position = LatLng(store.latitude, store.longtitude)
        }
    }
}