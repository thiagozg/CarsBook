package br.com.carsbook.firebase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by thiagozg on 09/02/2018.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    private val TAG = "firebase"

    // Obtêm o registration token.
    override fun onTokenRefresh() {
        val token = FirebaseInstanceId.getInstance().token
        token?.let {
            Log.d(TAG, "Refreshed token: " + it)
            sendRegistrationToServer(it)
        }
    }

    private fun sendRegistrationToServer(token: String) {
        // Aqui deve ficar a sua lógica para enviar o token para o servidor ...
    }
}
