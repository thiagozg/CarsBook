package br.com.carsbook.extensions

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import br.com.carsbook.BuildConfig
import br.com.carsbook.R

/**
 * Created by thiagozg on 15/02/2018.
 */

fun Context.createNotification(id: Int, intent: Intent, contentTitle: String, contentText: String) {
    val manager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Intent para disparar o broadcast
    val p = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    // Cria a notification
    val builder = NotificationCompat.Builder(this, "id")
            .setContentIntent(p)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true) // cancela automaticamente assim q abrir

    // Para cancelar
//    manager.cancel(id) // pelo id
//    manager.cancelAll() // todas

    // Dispara a notification
    val n = builder.build()
    manager.notify(id, n)

}

inline fun Context.debug(code: () -> Unit) {
    if (BuildConfig.BUILD_TYPE.equals("debug")) {
        code()
    }
}
