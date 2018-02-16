package br.com.carsbook.activity

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * Created by thiagozg on 21/11/2017.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected val context: Context
        get() = this

    protected fun postEB(any: Any) {
        EventBus.getDefault().post(any)
    }

}