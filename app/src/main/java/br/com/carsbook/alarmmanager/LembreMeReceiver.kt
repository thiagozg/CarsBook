package br.com.carsbook.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import br.com.carsbook.activity.MainActivity
import br.com.carsbook.extensions.createNotification
import java.util.*

/**
 * Created by thiagozg on 31/01/2018.
 */
class LembreMeReceiver : BroadcastReceiver() {

    companion object {
        const val TAG = "livroandroid"
        const val ACTION = "alarmmanager.LEMBRE_ME"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Você precisa comer: " + Date())

        val notifIntent = Intent(context, MainActivity::class.java)

        context.createNotification(1, notifIntent,
                "Hora de comer algo...",
                "Que tal uma fruta?")
    }
}