package br.com.carsbook.extensions

fun android.view.View.onClick(lambda: (view: android.view.View?) -> Unit) {
    setOnClickListener(lambda)
}
