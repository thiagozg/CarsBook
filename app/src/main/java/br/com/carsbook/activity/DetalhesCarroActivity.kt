package br.com.carsbook.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import br.com.carsbook.R
import br.com.carsbook.domain.event.FavoritoEvent
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.service.FavoritosService
import br.com.carsbook.extensions.addFragment
import br.com.carsbook.extensions.setupToolbar
import br.com.carsbook.fragments.MapaFragment
import br.com.livroandroid.carros.domain.retrofit.CarrosServiceRetrofit
import br.com.livroandroid.carros.extensions.loadUrl
import kotlinx.android.synthetic.main.activity_detalhes_carro.*
import kotlinx.android.synthetic.main.activity_detalhes_carro_content.*
import org.jetbrains.anko.*

/**
 * Created by thiagozg on 09/12/2017.
 */
class DetalhesCarroActivity : BaseActivity() {

    // somente quando a variavel 'carro' for utilizado é q ele será inicializado
    val carro: Carro by lazy { intent.getParcelableExtra<Carro>("carro") }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_detalhes_carro)
        setupToolbar(detalhesToolbar, carro.nome, true)
        initViews()
    }

    private fun initViews() {
        tDesc.text = carro.desc
        appBarImg.loadUrl(carro.urlFoto)

        fab.setOnClickListener{ onClickFavoritos(carro) }
        verificaSeCarroFavorito()

        imgVideo.loadUrl(carro.urlFoto)
        imgPlayVideo.setOnClickListener { onClickVideo() }

        val mapaFragment = MapaFragment()
        mapaFragment.arguments = intent.extras
        addFragment(R.id.mapaFragment, mapaFragment)
    }

    private fun onClickVideo() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(carro.urlVideo), "video/*")
        startActivity(intent)
    }

    private fun onClickFavoritos(carro: Carro) {
        doAsync {
            val favoritado = FavoritosService.favoritar(carro)
            uiThread {
                alteraFABColor(favoritado)
                toast(if (favoritado) R.string.msg_carro_favoritado
                      else R.string.msg_carro_desfavoritado)
                postEB(FavoritoEvent(carro))
            }
        }
    }

    private fun verificaSeCarroFavorito() {
        doAsync {
            alteraFABColor(FavoritosService.isFavorito(carro))
        }
    }

    private fun alteraFABColor(isFavorito: Boolean) {
        val fundo = ContextCompat.getColor(this,
                if (isFavorito) R.color.favorito_on
                else R.color.black)
        val cor = ContextCompat.getColor(this,
                if (isFavorito) R.color.yellow
                else R.color.favorito_off)
        fab.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(fundo))
        fab.setColorFilter(cor)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_editar -> {
                startActivity<CarroFormActivity>("carro" to carro)
                finish()
            }

            R.id.action_deletar -> {
                alert(R.string.msg_confirma_excluir_carro, R.string.app_name) {
                    positiveButton(R.string.sim) {
                        taskExcluir()
                    }
                    negativeButton(R.string.nao) { }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun taskExcluir() {
        doAsync {
            val response = CarrosServiceRetrofit.delete(carro)
            uiThread {
                toast(response.msg)
                finish()
                postEB(FavoritoEvent(carro))
            }
        }
    }

}