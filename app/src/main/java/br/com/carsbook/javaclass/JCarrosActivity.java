package br.com.carsbook.javaclass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import br.com.carsbook.R;
import br.com.carsbook.activity.BaseActivity;
import br.com.carsbook.domain.model.TipoCarro;
import br.com.carsbook.fragments.CarrosFragment;

/**
 * Created by thiagozg on 17/12/2017.
 */

public class JCarrosActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);
        toolbar = findViewById(R.id.mainToolbar);

        TipoCarro tipo = (TipoCarro) getIntent().getSerializableExtra("tipo");
        ActivityUtils.setupToolbar(this, toolbar, getString(tipo.getStringId()));

        if (savedInstanceState == null) {
            ActivityUtils.addFragment(getSupportFragmentManager(), getIntent(),
                    R.id.container, new CarrosFragment());
        }
    }

}
