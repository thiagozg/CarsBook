package br.com.livroandroid.carros.domain.retrofit

import android.util.Base64
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.Response
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.domain.service.FavoritosService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object CarrosServiceRetrofit {
    private val BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var service: ApiCarros

    // Classe Singleton, oq é necessario é inicializado nesse bloco, pois 'init' só é chamado 1x
    init {
        val retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
        service = retrofit.create(ApiCarros::class.java)
    }

    fun getCarros(tipo: TipoCarro): List<Carro> {
        val call = service.getCarros(tipo.name)
        return call.execute().body()
    }

    @JvmStatic
    fun save(carro: Carro): Response {
        val call = service.save(carro)
        return call.execute().body()
    }

    fun delete(carro: Carro): Response {
        val call = service.delete(carro.id)
        val response = call.execute().body()
        if (response.isOk())
            FavoritosService.desfavoritar(carro)
        return response
    }

    @JvmStatic
    fun postFoto(file: File): Response {
        val bytes = file.readBytes()
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        val call = service.sendPhoto(file.name, base64)
        return call.execute().body()
    }

}
