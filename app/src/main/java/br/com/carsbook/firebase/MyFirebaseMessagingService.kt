package br.com.carsbook.firebase

/**
 * Created by thiagozg on 09/02/2018.
 */

import android.content.Intent
import android.util.Log
import br.com.carsbook.R
import br.com.carsbook.activity.MainActivity
import br.com.carsbook.extensions.createNotification
import br.com.carsbook.extensions.toBundle
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "firebase"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived")

        // Verifica se a mensagem é do tipo dados (data messages)
        if (remoteMessage.data.isNotEmpty()) {
            val data = remoteMessage.data
            Log.d(TAG, "Dados: " + data)
            Log.d(TAG, "Nome: " + data.get("nome"))
            Log.d(TAG, "Sobrenome: " + data.get("sobrenome"))
        }

        // Verifica se a mensagem é do tipo notificação.
        remoteMessage.notification?.let {
            val title = it.title
            val msg = it.body
            Log.d(TAG, "Message Notification Title: " + title)
            Log.d(TAG, "Message Notification Body: " + msg)

            showNotification(remoteMessage, title, msg);
        }
    }

    private fun showNotification(remoteMessage: RemoteMessage, title: String?, msg: String?) {
        val intent = Intent(this, MainActivity::class.java)
        // Adiciona os parâmetros na intent
        intent.putExtras(remoteMessage.data.toBundle())

        applicationContext.createNotification(1, intent,
                title ?: getString(R.string.app_name),
                msg!!) // nunca sera nula se a notificacao tbm nao for
        Log.d(TAG, "Notificacao enviada, title: $title e msg: $msg")
    }
}