package br.com.carsbook.fragments

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.carsbook.R
import br.com.carsbook.activity.DetalhesCarroActivity
import br.com.carsbook.adapter.CarrosAdapter
import br.com.carsbook.domain.model.Carro
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.utils.AndroidUtil
import kotlinx.android.synthetic.main.fragment_carros.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by thiagozg on 10/12/2017.
 */
abstract class BaseListaFragment: BaseFragment() {

    private var tipo = TipoCarro.CLASSICOS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null)
            tipo = arguments.getSerializable("tipo") as TipoCarro
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_carros, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)
    }

    // Utilizar o onResume para fazer a logica de carregar os carros, pois nesse momento o usuario
    // já estará vendo a tela, e podemos exibir um progresso para ele
//    override fun onResume() {
//        super.onResume()
//        taskCarros()
//    }
    // -> Melhoria de Performance (dessa maneira a lista só sera carrega 1x)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskCarros()
    }

    open protected fun taskCarros() {
        if (AndroidUtil.isNetworkAvailable(this@BaseListaFragment.context)) {
            doAsync {
                val carros = getCarros(tipo)
                uiThread {
                    recyclerView.adapter = CarrosAdapter(carros) { onClickCarro(it) }
                }
            }
        }
    }

    abstract fun getCarros(tipoCarro: TipoCarro? = null): List<Carro>

    protected fun onClickCarro(carro: Carro) {
        activity.toast("Exibindo o carro: ${carro.nome}")
        activity.startActivity<DetalhesCarroActivity>("carro" to carro)
    }

}