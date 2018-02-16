package br.com.livroandroid.carros.domain

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import br.com.carsbook.R
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.TipoCarro
import br.com.livroandroid.carros.extensions.fromJson
import br.com.livroandroid.carros.extensions.getText
import br.com.livroandroid.carros.extensions.getXml
import org.json.JSONArray

@Deprecated("utilizando o Service com Retrofit.")
object CarrosServiceLocal {

    @Deprecated("utilizando webservices")
    fun getCarros(context: Context, tipo: TipoCarro, isJson: Boolean = true): List<Carro> {
        val raw = if (isJson) getArquivoRawByJSON(tipo) else getArquivoRawByXML(tipo)
        val inputStream = context.resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            val file = it.readText()
            val carros = if (isJson) fromJson(file) else parserXML(file)
            return carros
        }
    }

    private fun getArquivoRawByXML(tipo: TipoCarro) = when(tipo) {
        TipoCarro.CLASSICOS -> R.raw.xml_carros_classicos
        TipoCarro.ESPORTIVOS -> R.raw.xml_carros_esportivos
        else -> R.raw.xml_carros_luxo
    }

    private fun getArquivoRawByJSON(tipo: TipoCarro) = when(tipo) {
        TipoCarro.CLASSICOS -> R.raw.json_carros_classicos
        TipoCarro.ESPORTIVOS -> R.raw.json_carros_esportivos
        else -> R.raw.json_carros_luxo
    }

    @Deprecated("utilizando a extension")
    private fun parserJSON(jsonString: String): List<Carro> {
        val carros = mutableListOf<Carro>()
        val array = JSONArray(jsonString)

        for (i in 0..array.length() - 1) {
            val jsonCarro = array.getJSONObject(i)
            val carro = Carro()
            with(carro) {
                nome = jsonCarro.optString("nome")
                desc = jsonCarro.optString("desc")
                urlFoto = jsonCarro.optString("url_foto")
                carros.add(carro)
            }
        }

        return carros
    }

    private fun parserXML(xmlString: String): List<Carro> {
        val carros = mutableListOf<Carro>()
        val xml = xmlString.getXml()
        val nodeCarros = xml.getChildren("carro")

        for (node in nodeCarros) {
            val carro = Carro()
            with(carro) {
                nome = node.getText("nome")
                desc = node.getText("desc")
                urlFoto = node.getText("url_foto")
                carros.add(carro)
            }
        }

        Log.d(TAG, "${carros.size} carros encontrados.")
        return carros
    }

}