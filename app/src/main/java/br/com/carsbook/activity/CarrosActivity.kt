package br.com.carsbook.activity

import android.os.Bundle
import br.com.carsbook.R
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.extensions.addFragment
import br.com.carsbook.extensions.setupToolbar
import br.com.carsbook.fragments.CarrosFragment
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by thiagozg on 09/12/2017.
 */
class CarrosActivity : BaseActivity() {

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_carros)

        val tipo = intent.getSerializableExtra("tipo") as TipoCarro
        setupToolbar(mainToolbar, getString(tipo.stringId), true)

        if (icicle == null) {
            addFragment(R.id.container, CarrosFragment())
        }
    }

}