package br.com.carsbook.fragments

import br.com.carsbook.domain.event.CarroEvent
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.TipoCarro
import br.com.livroandroid.carros.domain.retrofit.CarrosServiceRetrofit
import org.greenrobot.eventbus.Subscribe

/**
 * Created by thiagozg on 09/12/2017.
 */
open class CarrosFragment: BaseListaFragment() {

    override fun getCarros(tipoCarro: TipoCarro?): List<Carro> {
        return CarrosServiceRetrofit.getCarros(tipoCarro!!)
    }

    @Subscribe
    fun onEventMainThread(event: CarroEvent) {
        taskCarros()
    }

}