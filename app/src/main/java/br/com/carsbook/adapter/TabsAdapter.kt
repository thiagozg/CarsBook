package br.com.carsbook.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.fragments.CarrosFragment
import br.com.carsbook.fragments.FavoritosFragment

/**
 * Created by thiagozg on 09/12/2017.
 */
class TabsAdapter (private val context: Context, fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    private val FAVORITOS_TAB = 3

    override fun getCount() = 4

    override fun getPageTitle(position: Int): CharSequence {
        val tipo = getTipoCarro(position)
        return context.getString(tipo.stringId)
    }


    override fun getItem(position: Int): Fragment {
        if (position == FAVORITOS_TAB) {
            return FavoritosFragment()
        }

        val tipo = getTipoCarro(position)
        val fragment = CarrosFragment()
        fragment.arguments = Bundle()
        fragment.arguments.putSerializable("tipo", tipo)

        return fragment
    }

    fun getTipoCarro(position: Int) = when(position) {
        0 -> TipoCarro.CLASSICOS
        1 -> TipoCarro.ESPORTIVOS
        2 -> TipoCarro.LUXO
        else -> TipoCarro.FAVORITOS
    }
}