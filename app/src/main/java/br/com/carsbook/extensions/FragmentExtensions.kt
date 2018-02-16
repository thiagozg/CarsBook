package br.com.carsbook.extensions

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

/**
 * Created by thiagozg on 03/01/2018.
 */
fun Fragment.validate(vararg permissions: String): Boolean {
    val list = ArrayList<String>()
    for (permission in permissions) {
        // Valida permissão
        val ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        if (!ok) {
            list.add(permission)
        }
    }
    if (list.isEmpty()) {
        // Tudo ok, retorna true
        return true
    }
    // Lista de permissões que falta acesso.
    val newPermissions = arrayOfNulls<String>(list.size)
    list.toArray(newPermissions)
    // Solicita permissão
    ActivityCompat.requestPermissions(activity, newPermissions, 1)
    return false
}