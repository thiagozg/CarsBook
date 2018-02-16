package br.com.carsbook.domain.event

import br.com.carsbook.domain.model.Carro

/**
 * Created by thiagozg on 31/01/2018.
 */

data class CarroEvent(val carro: Carro)
data class FavoritoEvent(val carro: Carro)