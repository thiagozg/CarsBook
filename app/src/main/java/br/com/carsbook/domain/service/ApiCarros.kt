package br.com.livroandroid.carros.domain.retrofit

import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.Response
import retrofit2.Call
import retrofit2.http.*

interface ApiCarros {
    @GET("tipo/{tipo}")
    fun getCarros(@Path("tipo") tipo: String): Call<List<Carro>>

    @POST("./")
    fun save(@Body carro: Carro): Call<Response>

    @DELETE("{id}")
    fun delete(@Path("id") id: Long): Call<Response>

    @FormUrlEncoded
    @POST("postFotoBase64")
    fun sendPhoto(@Field("fileName") fileName: String,
                  @Field("base64") base64: String): Call<Response>
}
