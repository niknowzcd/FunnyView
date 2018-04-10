package com.mysample.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.SeekBar;

import com.mysample.application.R;
import com.mysample.application.view.advertisementImageView.AdvertisementImageActivity;
import com.mysample.application.view.rippleButtom.RippleButtonActivity;

/**
 * Created by dly on 2018/1/9.
 */

public class MainActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        findViewById(R.id.btn_ripple).setOnClickListener(this);
        findViewById(R.id.btn_advertisement).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple:
                startActivity(new Intent(this, RippleButtonActivity.class));
                break;
            case R.id.btn_advertisement:
                startActivity(new Intent(this, AdvertisementImageActivity.class));
                break;
        }
    }
}
