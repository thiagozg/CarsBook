package br.com.carsbook.domain.model

/**
 * Created by thiagozg on 10/12/2017.
 */
data class Response (
        val id: Long,
        val status: String,
        val msg: String,
        val url: String) {

    fun isOk() = "OK".equals(status, ignoreCase = true)

}