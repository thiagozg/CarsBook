package br.com.carsbook.domain.model

import br.com.carsbook.R

/**
 * Created by thiagozg on 30/11/2017.
 */
enum class TipoCarro(val stringId: Int) {
    CLASSICOS(R.string.classicos),
    ESPORTIVOS(R.string.esportivos),
    LUXO(R.string.luxo),
    FAVORITOS(R.string.favoritos)
}