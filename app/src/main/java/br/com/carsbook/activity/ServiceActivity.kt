package br.com.carsbook.activity

import android.content.Intent
import android.os.Bundle
import br.com.carsbook.R
import br.com.carsbook.androidservice.TestService
import br.com.carsbook.extensions.onClick
import kotlinx.android.synthetic.main.activity_service.*

/**
 * Created by thiagozg on 31/01/2018.
 */
class ServiceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        btStart.onClick { startService(Intent(this, TestService::class.java)) }
        btStop.onClick {
            stopService(Intent(this, TestService::class.java))
            // ou poderia ser usado stopSelf() do proprio Servico
        }
    }
}