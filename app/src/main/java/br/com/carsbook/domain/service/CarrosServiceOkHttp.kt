package br.com.livroandroid.carros.domain

import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.Response
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.utils.HttpUtil
import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.extensions.toJson

@Deprecated("utilizando o Service com Retrofit.")
object CarrosServiceOkHttp {
    private val BASE_URL = "http://livrowebservices.com.br/rest/carros"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val url = "$BASE_URL/tipo/${tipo.name}"
        val json = HttpUtil.get(url)
        val carros = fromJson<List<Carro>>(json)
        return carros
    }

    // Salva um carro
    fun save(carro: Carro): Response {
        // Faz POST do JSON carro
        val json = HttpUtil.post(BASE_URL, carro.toJson())
        val response = fromJson<Response>(json)
        return response
    }

    // Deleta um carro
    fun delete(carro: Carro): Response {
        val url = "$BASE_URL/${carro.id}"
        val json = HttpUtil.delete(url)
        val response = fromJson<Response>(json)
        return response
    }

}