package br.com.carsbook

import android.app.Application
import android.util.Log

/**
 * Created by thiagozg on 21/11/2017.
 */
class CarrosApplication : Application() {

    private val TAG = javaClass.name

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: CarrosApplication? = null

        fun getInstance(): CarrosApplication {
            if (appInstance == null) {
                throw IllegalStateException("Não iniciado na classe Application.")
            }

            return appInstance!!
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Log.d(TAG, "onTerminate()")
    }
}