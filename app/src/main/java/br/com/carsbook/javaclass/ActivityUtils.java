package br.com.carsbook.javaclass;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by thiagozg on 15/02/2018.
 */

public final class ActivityUtils {

    private ActivityUtils() {
    }

    public static void setupToolbar(AppCompatActivity activity, Toolbar toolbar, String title) {
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
            if (title != null) {
                activity.getSupportActionBar().setTitle(title);
            }
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public static void addFragment(FragmentManager fragmentManager, Intent intent,
                                   @IdRes int layoutId, Fragment fragment) {
        fragment.setArguments(intent.getExtras());
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(layoutId, fragment);
        ft.commit();
    }

    public static void loadUrl(Context context, ImageView imageView, String url,
                               final @Nullable ProgressBar progress) {
        if (url == null || url.trim().isEmpty()) {
            imageView.setImageBitmap(null);
            return;
        }
        if (progress == null) {
            Picasso.with(context).load(url).fit().into(imageView);
        } else {
            progress.setVisibility(View.VISIBLE);
            Picasso.with(context).load(url).fit().into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    progress.setVisibility(View.GONE);

                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);

                }
            });
        }
    }
}
