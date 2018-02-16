package br.com.carsbook.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.carsbook.R
import br.com.carsbook.domain.model.Carro
import br.com.livroandroid.carros.extensions.loadUrl
import kotlinx.android.synthetic.main.item_carro.view.*

/**
 * Created by thiagozg on 09/12/2017.
 */
class CarrosAdapter(
        val carros: List<Carro>,
        val onClick: (Carro) -> Unit) :
        RecyclerView.Adapter<CarrosAdapter.CarrosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrosViewHolder {
        val view = LayoutInflater.from(parent.context)
                                 .inflate(R.layout.item_carro, parent, false)
        return CarrosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.carros.size
    }

    override fun onBindViewHolder(holder: CarrosViewHolder, position: Int) {
        val carro = carros[position]
        with(holder.itemView) {
            tNome.text = carro.nome
            img.loadUrl(carro.urlFoto, progress)
            setOnClickListener { onClick(carro) }
        }
    }

    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
