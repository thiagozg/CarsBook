package br.com.carsbook.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.com.carsbook.R

/**
 * Created by thiagozg on 21/01/2018.
 */
class BroadcastReceiverActivity : BaseActivity() {

    private val helloReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Log.d("livroandroid", "HelloReceiver Dinamico!!!")
            val text = findViewById<TextView>(R.id.text)
            text.text = "Mensagem recebida pelo HelloReceiver Dinamico!!!"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver)

        // Enviar broadcast
        findViewById<Button>(R.id.btEnviar).setOnClickListener {
            sendBroadcast(Intent("BINGO"))
            Toast.makeText(this, "Intent enviada!", Toast.LENGTH_SHORT).show()
        }

        // Registra o receiver
        registerReceiver(helloReceiver, IntentFilter("BINGO"))
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancela o receiver
        unregisterReceiver(helloReceiver)
    }

}