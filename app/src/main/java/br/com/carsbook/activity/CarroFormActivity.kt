package br.com.carsbook.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.carsbook.R
import br.com.carsbook.domain.event.CarroEvent
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.extensions.isEmpty
import br.com.carsbook.extensions.onClick
import br.com.carsbook.extensions.setupToolbar
import br.com.carsbook.extensions.string
import br.com.livroandroid.carros.domain.retrofit.CarrosServiceRetrofit
import br.com.livroandroid.carros.extensions.loadUrl
import br.com.livroandroid.carros.utils.CameraHelper
import kotlinx.android.synthetic.main.activity_carro_form.*
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {

    // O carro pode ser nulo no caso de um Novo Carro
    val carro: Carro? by lazy { intent.getParcelableExtra<Carro>("carro") }
    val camera by lazy { CameraHelper() }

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_carro_form)
        setupToolbar(formToolbar, carro?.nome ?: getString(R.string.novo_carro))
        initViews()
        camera.init(icicle)
    }

    fun initViews() {
        appBarImg.onClick { onClickClickBarImage() }

        // A função apply somente é executada se o objeto não for nulo
        carro?.apply {
            appBarImg.loadUrl(urlFoto)
            tNome.string = nome
            tDesc.string = desc
            when (tipo) {
                TipoCarro.CLASSICOS.name.toLowerCase() -> radioTipo.check(R.id.tipoClassico)
                TipoCarro.ESPORTIVOS.name.toLowerCase() -> radioTipo.check(R.id.tipoEsportivo)
                else -> radioTipo.check(R.id.tipoLuxo)
            }
        }
    }

    private fun onClickClickBarImage() {
        val ms = System.currentTimeMillis()
        val fileName =
                if (carro != null) "foto_carro_${carro?.id}.jpg"
                else "foto_carro${ms}.jpg"
        val intent = camera.open(this, fileName)
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val bitmap = camera.getBitmap(600, 600)
            bitmap?.let {
                camera.save(bitmap)
                appBarImg.setImageBitmap(bitmap)
            }
        }
    }

    private fun taskSalvar() {
        if (!camposValidos()) return
        doAsync {
            // Cria um carro para salvar/atualizar
            val c = carro ?: Carro()
            c.nome = tNome.string
            c.desc = tDesc.string
            c.tipo = when (radioTipo.checkedRadioButtonId) {
                R.id.tipoClassico -> TipoCarro.CLASSICOS.name
                R.id.tipoEsportivo -> TipoCarro.ESPORTIVOS.name
                else -> TipoCarro.LUXO.name
            }

            val file = camera.file
            file?.apply {
                val response = CarrosServiceRetrofit.postFoto(file)
                if (response.isOk())
                    c.urlFoto = response.url
            }

            val response = CarrosServiceRetrofit.save(c)
            uiThread {
                toast(response.msg)
                finish()
                postEB(CarroEvent(c))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        camera.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_carro, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_salvar -> taskSalvar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun camposValidos(): Boolean {
        if (tNome.isEmpty()) {
            tNome.error = getString(R.string.msg_error_form_nome)
            return false
        }
        if (tDesc.isEmpty()) {
            tDesc.error = getString(R.string.msg_error_form_desc)
            return false
        }
        return true
    }
}
