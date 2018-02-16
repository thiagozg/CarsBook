package br.com.carsbook.extensions

import android.os.Bundle

/**
 * Created by thiagozg on 14/02/2018.
 */
fun Map<String,String>.toBundle(): Bundle {
    val bundle = Bundle()
    for (key in keys) {
        bundle.putString(key, get(key))
    }
    return bundle
}