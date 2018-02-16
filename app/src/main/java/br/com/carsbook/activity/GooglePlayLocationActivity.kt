package br.com.carsbook.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import br.com.carsbook.R
import br.com.carsbook.extensions.createNotification
import br.com.carsbook.extensions.validate
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_google_play_location.*
import org.jetbrains.anko.toast
import java.text.DateFormat
import java.util.*

class GooglePlayLocationActivity : BaseActivity(), OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    var map: GoogleMap? = null
    var mapFragment: SupportMapFragment? = null
    var mGoogleApiClient: GoogleApiClient? = null

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        setContentView(R.layout.activity_google_play_location)

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment?.getMapAsync(this)

        // Configura o objeto GoogleApiClient
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API) // caso queira usar o Drive, e´ so´ alterar aqui
                .build()

        // Solicita as permissões
        validate(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION)

        btMyLocation.setOnClickListener { getMyLocation() }
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        // Última localização
        val l = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient)

        Log.d(TAG, "lastLocation: " + l)

        // Atualiza a localização do mapa
        setMapLocation(l)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada...
                alertAndFinish()
                return
            }
        }
    }

    private fun alertAndFinish() {
        run {
            AlertDialog.Builder(this)
                    .setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.")
                    .setPositiveButton("OK") { dialog, id -> finish() }
                    .create()
                    .show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "onMapReady: " + map)
        this.map = map

        // Configura o tipo do mapa
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
    }

    override fun onStart() {
        super.onStart()
        // Conecta no Google Play Services
        mGoogleApiClient?.connect()
    }

    override fun onStop() {
        // Para o GPS
        stopLocationUpdates()

        // Desconecta
        mGoogleApiClient?.disconnect()
        super.onStop()
    }

    override fun onConnected(bundle: Bundle?) {
        toast("Conectado no Google Play Services!")

        // Inicia o GPS
        startLocationUpdates()
    }

    override fun onConnectionSuspended(cause: Int) {
        toast("Conexão interrompida.")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        toast("Erro ao conectar: " + connectionResult)
    }

    // Atualiza a coordenada do mapa
    private fun setMapLocation(location: Location?) {
        if (map != null && location != null) {
            val latLng = LatLng(location.latitude, location.longitude)
            val update = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
            map?.animateCamera(update)

            Log.d(TAG, "setMapLocation: " + location)
            val text = findViewById<TextView>(R.id.text)
            var s = String.format("Última atualização %s", DateFormat.getTimeInstance().format(Date()))
            s += String.format("\nLat/Lnt %f/%f, provider: %s", location.latitude, location.longitude, location.provider)
            text.text = s

            // Criando Notificação
            sendNotification(s)

            // Desenha uma bolinha vermelha
            val circle = CircleOptions().center(latLng)
            circle.fillColor(Color.RED)
            circle.radius(25.0) // Em metros
            map?.clear()
            map?.addCircle(circle)
        }
    }

    private fun sendNotification(msg: String) {
        // ao clicar abre a MainActivity
        val intent = Intent(this, MainActivity::class.java)
        createNotification(1, intent, "Notificação Teste", msg)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        Log.d(TAG, "startLocationUpdates()")
        val locRequest = LocationRequest()
        locRequest.interval = 10000
        locRequest.fastestInterval = 5000
        locRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locRequest, this)
    }

    private fun stopLocationUpdates() {
        Log.d(TAG, "stopLocationUpdates()")
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "onLocationChanged(): " + location)
        // Nova localizaçao: atualiza a interface
        setMapLocation(location)
    }

    companion object {
        private const val TAG = "livroandroid"
    }

}
