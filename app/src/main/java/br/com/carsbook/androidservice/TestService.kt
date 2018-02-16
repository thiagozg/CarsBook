package br.com.carsbook.androidservice

import android.app.IntentService
import android.content.Intent
import android.util.Log
import br.com.carsbook.activity.MainActivity
import br.com.carsbook.extensions.createNotification

/**
 * Created by thiagozg on 31/01/2018.
 */
class TestService : IntentService("TestService") {

    private var count: Int = 0
    private var running: Boolean = false

    companion object {
        const private val MAX = 10
        const private val TAG = "livro"
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, ">> TestService.onHandleIntent()")
        running = true

        while (running && count < MAX) {
            Thread.sleep(1000) // Simula algum processamento
            Log.d(TAG, "TestService executando... " + count)
            count++
        }

        Log.d(TAG, "<< TestService.onHandleIntent()")
        val intent = Intent(this, MainActivity::class.java)
        applicationContext.createNotification(1, intent, "Livro Android", "Fim do serviço.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "TestService.onDestroy()")
        running = false
    }

}