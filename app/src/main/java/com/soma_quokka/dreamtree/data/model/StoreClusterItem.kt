package com.soma_quokka.dreamtree.data.model

import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class StoreClusterItem(var position: LatLng) : TedClusterItem {

    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(position.latitude, position.longitude)
    }

    var title: String? = null
    var type: String? = null
    var snippet: String? = null

    constructor(lat: Double, lng: Double) : this(LatLng(lat, lng)) {
        this.title = null
        this.type =  null
        this.snippet = null
    }

    constructor(lat: Double, lng: Double, title: String?, type: String?) : this(LatLng(lat, lng)) {
        this.title = title
        this.type =  type
        this.snippet = null
    }

    constructor(lat: Double, lng: Double, title: String?, type: String?, snippet: String?) : this(
        LatLng(lat, lng)
    ) {
        this.title = title
        this.type = type
        this.snippet = snippet
    }
}
