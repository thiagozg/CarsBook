package br.com.carsbook.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.carsbook.activity.MainActivity
import br.com.carsbook.extensions.createNotification

/**
 * Created by thiagozg on 21/01/2018.
 */
class HelloReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("livroandroid", "Hello Receiver!")
        // Cria uma notificacao
        val intent2 = Intent(context, MainActivity::class.java)
        context?.createNotification(1, intent2, "Livro Android", "Hello Receiver")
    }

}