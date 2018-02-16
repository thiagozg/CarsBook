package br.com.carsbook.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.carsbook.R
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.extensions.validate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by thiagozg on 03/01/2018.
 */
class MapaFragment : BaseFragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null
    val carro: Carro by lazy { arguments.getParcelable<Carro>("carro") }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inicia o mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // é chamado quando a inicializadao do mapa estiver OK
    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        this.map = map

        // levando o usuario para a propria localizacao
        if (carro.latitude == "0.0") {
            val ok = validate(Manifest.permission.ACCESS_FINE_LOCATION,
                              Manifest.permission.ACCESS_COARSE_LOCATION)

            if (ok) {
                map.isMyLocationEnabled = true
            }

        } else {
            val location = LatLng(carro.latitude.toDouble(), carro.longitude.toDouble())

            // posiciona o mapa na coordenada (zoom = 13)
            val update = CameraUpdateFactory.newLatLngZoom(location, 13F)
            map.moveCamera(update)
            map.addMarker(MarkerOptions()
                    .title(carro.nome)
                    .snippet(carro.desc)
                    .position(location))
            // tipo do mapa: normal, satelite, terreno ou hibrido
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                map?.isMyLocationEnabled = true
                return
            }
        }
    }
}