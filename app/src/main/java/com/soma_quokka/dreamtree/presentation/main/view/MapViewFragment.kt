package com.soma_quokka.dreamtree.presentation.main.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.data.model.StoreClusterItem
import com.soma_quokka.dreamtree.data.model.StoreList
import com.soma_quokka.dreamtree.presentation.main.MapTypeConstant
import ted.gun0912.clustering.naver.TedNaverClustering

class MapViewFragment : Fragment(), OnMapReadyCallback {

    private var storeList: StoreList? = null
    private val ARG_PARAM = "storeList"
    lateinit var naverMap: NaverMap

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
            storeList = it.getParcelable(ARG_PARAM)
        }

        return inflater.inflate(R.layout.activity_map, container, false)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        val storeType = MapTypeConstant
        val cameraPosition = CameraPosition(LatLng(37.58, 126.90), 14.0)
        naverMap.cameraPosition = cameraPosition

        naverMap.uiSettings.isCompassEnabled = false

        val marker = Marker()
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = Color.RED
        marker.position = LatLng(37.58, 126.90)
        marker.map = naverMap

        storeList?.let { getItems(it) }?.let {
            TedNaverClustering.with<StoreClusterItem>(requireActivity(), naverMap)
                .customMarker { clusterItem ->
                    Marker(clusterItem.position).apply {
                        when (clusterItem.type) {
                            storeType.BAKERY -> icon = OverlayImage.fromResource(R.drawable.ic_bakery)
                            storeType.CHINESE_FOOD -> icon = OverlayImage.fromResource(R.drawable.ic_chinese_food)
                            storeType.FAST_FOOD -> icon = OverlayImage.fromResource(R.drawable.ic_fast_food)
                            storeType.JAPANESE_FOOD -> icon = OverlayImage.fromResource(R.drawable.ic_japanese_food)
                            storeType.KOREAN_FOOD -> icon = OverlayImage.fromResource(R.drawable.ic_korean_food)
                            storeType.WESTERN_FOOD -> icon = OverlayImage.fromResource(R.drawable.ic_western_food)
                        }
                    }

                }
                .clusterText { it.toString() }
                .clusterBackground { ContextCompat.getColor(requireContext(),R.color.indigo) }
                .items(it)
                .make()
        }
    }

    private fun getItems(storeResponse: StoreList): MutableList<StoreClusterItem> {
        var stores = mutableListOf<StoreClusterItem>()
        for(store in storeResponse.storeList){
            stores.add(StoreClusterItem(store.latitude, store.longitude, store.name, store.type))
        }
        return stores
    }
}