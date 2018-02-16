package br.com.carsbook.extensions

import android.content.pm.PackageManager
import android.support.annotation.IdRes
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

/**
 * Created by thiagozg on 21/11/2017.
 */
fun AppCompatActivity.onClick(@IdRes viewId: Int, onClick: (v: View?) -> Unit) {
    val view = findViewById<View>(viewId)
    view.setOnClickListener { onClick(it) }
}

fun AppCompatActivity.setupToolbar(toolbar: Toolbar?, title: String? = null,
                                   upNavigation: Boolean = true): ActionBar {
    toolbar.let {
        this.setSupportActionBar(toolbar)
        title.let { supportActionBar?.title = title }
        supportActionBar?.setDisplayHomeAsUpEnabled(upNavigation)
    }
    return supportActionBar!!
}

fun AppCompatActivity.addFragment(@IdRes layoutId: Int, fragment: Fragment, isReplacement: Boolean = false) {
    fragment.arguments = intent.extras
    val ft = supportFragmentManager.beginTransaction()
    if (isReplacement)
        ft.replace(layoutId, fragment)
    else
        ft.add(layoutId, fragment)
    ft.commit()
}

fun AppCompatActivity.validate(vararg permissions: String): Boolean {
    val list = ArrayList<String>()
    for (permission in permissions) {
        // Valida permissão
        val ok = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
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
    ActivityCompat.requestPermissions(this, newPermissions, 1)
    return false
}

