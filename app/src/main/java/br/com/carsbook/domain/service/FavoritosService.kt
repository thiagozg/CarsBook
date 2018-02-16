package br.com.carsbook.domain.service

import br.com.carsbook.domain.dao.DatabaseManager
import br.com.carsbook.domain.model.Carro

object FavoritosService {

    private val carrosDAO by lazy { DatabaseManager.getCarroDAO() }

    fun getCarros(): List<Carro> {
        return carrosDAO.findAll()
    }

    fun favoritar(carro: Carro): Boolean {
        if (isFavorito(carro)) {
            desfavoritar(carro)
            return false
        } else {
            carrosDAO.insert(carro)
            return true
        }
    }

    fun desfavoritar(carro: Carro) = carrosDAO.delete(carro)

    fun isFavorito(carro: Carro): Boolean {
        return carrosDAO.getById(carro.id) != null
    }

}
