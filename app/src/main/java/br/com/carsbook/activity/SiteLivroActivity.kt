package br.com.carsbook.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import br.com.carsbook.R
import br.com.carsbook.activity.dialogs.AboutDialog
import br.com.carsbook.extensions.setupToolbar
import kotlinx.android.synthetic.main.activity_site_livro.*
import kotlinx.android.synthetic.main.toolbar.*

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "http://livroandroid.com.br/sobre.htm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_livro)
        setupToolbar(mainToolbar, "Sobre")
        setupWebViewClient()
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeToResfresh.setOnRefreshListener { webview.reload() }
        swipeToResfresh.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3
        )
    }

    private fun setupWebViewClient() {
        webview.webViewClient = configureWebViewClient()
        webview.loadUrl(URL_SOBRE)
    }

    private fun configureWebViewClient(): WebViewClient {
        return object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = GONE
                swipeToResfresh.isRefreshing = false
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                if (url.endsWith("sobre.htm")) {
                    AboutDialog.showAbout(supportFragmentManager)
                    return true
                }

                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}
