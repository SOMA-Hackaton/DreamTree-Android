package com.soma_quokka.dreamtree.presentation.main.view

import android.content.Intent
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
import com.soma_quokka.dreamtree.data.model.StoreList
import com.soma_quokka.dreamtree.data.response.StoreResponseItem
import com.soma_quokka.dreamtree.presentation.main.MapTypeConstant
import com.soma_quokka.dreamtree.presentation.store_detail.StoreDetailActivity
import ted.gun0912.clustering.naver.TedNaverClustering

class MapViewFragment : Fragment(), OnMapReadyCallback {

    companion object{
        val STORE_ITEM = "STORE_ITEM"
        val ARG_PARAM = "storeList"
    }

    private var storeList: StoreList? = null
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

        storeList?.let { it.storeList }?.let {
            TedNaverClustering.with<StoreResponseItem>(requireActivity(), naverMap)
                .customMarker { clusterItem ->
                    Marker(LatLng(clusterItem.latitude,clusterItem.longitude)).apply {
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
                .markerClickListener {
                    val intent = Intent(requireContext(), StoreDetailActivity::class.java)
                    intent.putExtra(STORE_ITEM, it)
                    startActivity(intent)
                }
                .clusterText { it.toString() }
                .clusterBackground { ContextCompat.getColor(requireContext(),R.color.indigo) }
                .items(it)
                .make()
        }
    }
}