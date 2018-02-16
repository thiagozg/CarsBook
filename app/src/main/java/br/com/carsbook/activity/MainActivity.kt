package br.com.carsbook.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import br.com.carsbook.R
import br.com.carsbook.adapter.TabsAdapter
import br.com.carsbook.domain.model.TipoCarro
import br.com.carsbook.extensions.setupToolbar
import br.com.carsbook.utils.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(mainToolbar)
        setupNavDrawer()
        setupViewPagerTabs()
        fab.setOnClickListener { startActivity<CarroFormActivity>() }
        getFirebaseNotification()
    }

    private fun setupViewPagerTabs() {
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = TabsAdapter(context, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        val color = ContextCompat.getColor(context, R.color.white)
        tabLayout.setTabTextColors(color, color)

        viewPager.currentItem = PreferenceUtil.tabIdx
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) { }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) { }
            override fun onPageSelected(position: Int) {
                PreferenceUtil.tabIdx = position
            }
        })
    }

    private fun setupNavDrawer() {
        val toggle = ActionBarDrawerToggle(
                this, mainDrawer, mainToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    private fun getFirebaseNotification() {
        Log.d("firebase", "Nome: ${intent.getStringExtra("nome")}" +
                "Sobrenome: ${intent.getStringExtra("sobrenome")}")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_carros_classicos -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.CLASSICOS)
            }
            R.id.nav_item_carros_esportivos -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.ESPORTIVOS)
            }
            R.id.nav_item_carros_luxo -> {
                startActivity<CarrosActivity>("tipo" to TipoCarro.LUXO)
            }
            R.id.nav_item_site_livro -> {
                startActivity<SiteLivroActivity>()
            }
            R.id.nav_item_sound -> {
                val player = MediaPlayer.create(this, R.raw.test_sound)
                player.start()
                Thread.sleep(3000)
                player.release()
            }
            R.id.nav_item_google_play_services -> {
                startActivity<GooglePlayLocationActivity>()
            }
            R.id.nav_item_broadcast_receiver_estatico -> {
                sendBroadcast(Intent("BINGO"))
                toast("Intent enviada!")
            }
            R.id.nav_item_broadcast_receiver_dinamico -> {
                startActivity<BroadcastReceiverActivity>()
            }
            R.id.nav_item_service -> {
                startActivity<ServiceActivity>()
            }
            R.id.nav_item_alarm_manager -> {
                startActivity<AlarmManagerActivity>()
            }
            R.id.nav_item_settings -> {
                toast("Clicou em configurações")
            }
        }

        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }
}
