package br.com.carsbook.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by thiagozg on 10/12/2017.
 */
object AndroidUtil {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivity.allNetworks
        return networks.map { connectivity.getNetworkInfo(it) }
                       .any { it.state == NetworkInfo.State.CONNECTED }
    }

    @Deprecated("utilizando a funcao com lambdas")
    fun isNetworkAvailableDeprecated(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networks = connectivity.allNetworks
        for (n in networks) {
            val info = connectivity.getNetworkInfo(n)
            if (info.state == NetworkInfo.State.CONNECTED)
                return true
        }

        return false
    }
}