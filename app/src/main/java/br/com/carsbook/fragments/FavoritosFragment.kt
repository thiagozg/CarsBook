package br.com.carsbook.fragments

import br.com.carsbook.domain.event.FavoritoEvent
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.domain.service.FavoritosService
import org.greenrobot.eventbus.Subscribe

class FavoritosFragment : BaseListaFragment() {

    override fun getCarros(tipoCarro: TipoCarro?): List<Carro> {
        return FavoritosService.getCarros()
    }

    @Subscribe
    fun onEventMainThread(event: FavoritoEvent) {
        taskCarros()
    }

}
