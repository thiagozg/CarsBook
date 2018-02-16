package br.com.carsbook.javaclass;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import br.com.carsbook.R;
import br.com.carsbook.activity.AlarmManagerActivity;
import br.com.carsbook.activity.BaseActivity;
import br.com.carsbook.activity.BroadcastReceiverActivity;
import br.com.carsbook.activity.GooglePlayLocationActivity;
import br.com.carsbook.activity.ServiceActivity;
import br.com.carsbook.activity.SiteLivroActivity;
import br.com.carsbook.adapter.TabsAdapter;
import br.com.carsbook.domain.model.TipoCarro;
import br.com.carsbook.utils.PreferenceUtil;

/**
 * Created by thiagozg on 17/12/2017.
 */

public class JMainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout mainDrawer;
    private NavigationView navView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        ActivityUtils.setupToolbar(this, toolbar, null);
        setupNavDrawer();
        setupViewPagerTabs();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JMainActivity.this, JCarroFormActivity.class);
                startActivity(intent);
            }
        });

        getFirebaseNotification();
    }

    private void initViews() {
        toolbar = findViewById(R.id.mainToolbar);
        fab = findViewById(R.id.fab);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        mainDrawer = findViewById(R.id.mainDrawer);
        navView = findViewById(R.id.navView);
    }

    private void setupViewPagerTabs() {
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new TabsAdapter(getContext(), getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        int color = ContextCompat.getColor(getContext(), R.color.white);
        tabLayout.setTabTextColors(color, color);

        viewPager.setCurrentItem(PreferenceUtil.getTabIdx());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                PreferenceUtil.setTabIdx(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    private void setupNavDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mainDrawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mainDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    private void getFirebaseNotification() {
        Log.d("firebase", "Nome: " + getIntent().getStringExtra("nome") +
                "Sobrenome: " + getIntent().getStringExtra("sobrenome"));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.nav_item_carros_classicos:
                intent = new Intent(getContext(), JCarrosActivity.class);
                intent.putExtra("tipo", TipoCarro.CLASSICOS);
                startActivity(intent);
            	break;

            case R.id.nav_item_carros_esportivos:
                intent = new Intent(getContext(), JCarrosActivity.class);
                intent.putExtra("tipo", TipoCarro.ESPORTIVOS);
                startActivity(intent);
            	break;

            case R.id.nav_item_carros_luxo:
                intent = new Intent(getContext(), JCarrosActivity.class);
                intent.putExtra("tipo", TipoCarro.LUXO);
                startActivity(intent);
            	break;

            case R.id.nav_item_site_livro:
                intent = new Intent(getContext(), SiteLivroActivity.class);
                startActivity(intent);
            	break;

            case R.id.nav_item_sound:
                MediaPlayer player = MediaPlayer.create(this, R.raw.test_sound);
                player.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.release();
            	break;

            case R.id.nav_item_google_play_services:
                intent = new Intent(getContext(), GooglePlayLocationActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_item_broadcast_receiver_estatico:
                sendBroadcast(new Intent("BINGO"));
                Toast.makeText(getContext(), "Intent enviada!", Toast.LENGTH_SHORT)
                        .show();
                break;

            case R.id.nav_item_broadcast_receiver_dinamico:
                intent = new Intent(getContext(), BroadcastReceiverActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_item_service:
                intent = new Intent(getContext(), ServiceActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_item_alarm_manager:
                intent = new Intent(getContext(), AlarmManagerActivity.class);
                startActivity(intent);
                break;


            case R.id.nav_item_settings:
                Toast.makeText(getContext(), "Clicou em configurações", Toast.LENGTH_SHORT)
                     .show();
            	break;
        }

        mainDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
