package br.com.carsbook.javaclass;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import br.com.carsbook.R;
import br.com.carsbook.activity.BaseActivity;
import br.com.carsbook.domain.event.CarroEvent;
import br.com.carsbook.domain.model.Carro;
import br.com.carsbook.domain.model.Response;
import br.com.carsbook.domain.model.TipoCarro;
import br.com.livroandroid.carros.domain.retrofit.CarrosServiceRetrofit;
import br.com.livroandroid.carros.utils.CameraHelper;

/**
 * Created by thiagozg on 17/12/2017.
 */

public class JCarroFormActivity extends BaseActivity {

    // O carro pode ser nulo no caso de um Novo Carro
    private Carro carro;
    private Toolbar toolbar;
    private CameraHelper camera;
    private ImageView appBarImg;
    private TextView tNome;
    private TextView tDesc;
    private RadioGroup radioTipo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro_form);
        initViews();
        if (carro != null) {
            ActivityUtils.setupToolbar(this, toolbar, carro.getNome());
        } else {
            ActivityUtils.setupToolbar(this, toolbar, getString(R.string.novo_carro));
        }
        camera.init(savedInstanceState);
    }

    private void initViews() {
        carro = getIntent().getParcelableExtra("carro");
        camera = new CameraHelper();
        toolbar = findViewById(R.id.formToolbar);
        appBarImg = findViewById(R.id.appBarImg);
        tNome = findViewById(R.id.tNome);
        tDesc = findViewById(R.id.tDesc);
        radioTipo = findViewById(R.id.radioTipo);

        appBarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickClickBarImage();
            }
        });

        if (carro != null) {
            ActivityUtils.loadUrl(this, appBarImg, carro.getUrlFoto(), null);
            tNome.setText(carro.getNome());
            tDesc.setText(carro.getDesc());

            if (carro.getTipo().equals(TipoCarro.CLASSICOS.name().toLowerCase())) {
                radioTipo.check(R.id.tipoClassico);
            } else if (carro.getTipo().equals(TipoCarro.ESPORTIVOS.name().toLowerCase())) {
                radioTipo.check(R.id.tipoEsportivo);
            } else {
                radioTipo.check(R.id.tipoLuxo);
            }
        }
    }

    private void onClickClickBarImage() {
        long ms = System.currentTimeMillis();
        String fileName = carro != null ?
                "foto_carro_"+carro.getId()+".jpg" : "foto_carro"+ms+".jpg";
        Intent intent = camera.open(this, fileName);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = camera.getBitmap(600, 600);
            if (bitmap != null) {
                camera.save(bitmap);
                appBarImg.setImageBitmap(bitmap);
            }
        }
    }

    private void taskSalvar() {
        if (!camposValidos()) return;
        new DoAsyncPostFoto().execute();
    }

    private class DoAsyncPostFoto extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Cria um carro para salvar/atualizar
            final Carro c = carro != null ? carro : new Carro();
            c.setNome(tNome.getText().toString());
            c.setDesc(tDesc.getText().toString());

            switch (radioTipo.getCheckedRadioButtonId()) {
                case R.id.tipoClassico:
                    c.setTipo(TipoCarro.CLASSICOS.name());
                    break;
                case R.id.tipoEsportivo:
                    c.setTipo(TipoCarro.ESPORTIVOS.name());
                    break;
                default:
                    c.setTipo(TipoCarro.LUXO.name());
                    break;
            }

            File file = camera.getFile();
            if (file != null) {
                Response response = CarrosServiceRetrofit.postFoto(file);
                if (response.isOk())
                    c.setUrlFoto(response.getUrl());
            }

            final Response response = CarrosServiceRetrofit.save(c);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(JCarroFormActivity.this, response.getMsg(), Toast.LENGTH_SHORT)
                            .show();
                    finish();
                    postEB(new CarroEvent(c));
                }
            });


            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        camera.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_carro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            switch (item.getItemId()) {
                case R.id.action_salvar:
                    taskSalvar();
                    break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean camposValidos() {
        if (tNome.getText().toString().isEmpty()) {
            tNome.setError(getString(R.string.msg_error_form_nome));
            return false;
        }
        if (tDesc.getText().toString().isEmpty()) {
            tDesc.setError(getString(R.string.msg_error_form_desc));
            return false;
        }
        return true;
    }

}
