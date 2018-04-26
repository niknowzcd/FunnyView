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
import com.mysample.application.view.advertisementImageView.AdvertisementImageActivityKt;
import com.mysample.application.view.advertisementImageView.AdvertisementImageView;
import com.mysample.application.view.numberRainView.NumberRainActivity;
import com.mysample.application.view.rippleButtom.RippleButtonActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dly on 2018/1/9.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Map<String, Class> subscriberClassByMethodKey = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);

        findViewById(R.id.btn_ripple).setOnClickListener(this);
        findViewById(R.id.btn_advertisement).setOnClickListener(this);
        findViewById(R.id.btn_number_rain).setOnClickListener(this);


        EventBus.getDefault().post("12456");

    }

    @Subscribe
    public void onMainEvent(String str) {
        System.out.println("event = " + str);
        System.out.println("event map = " + subscriberClassByMethodKey.keySet());
    }

    @Subscribe
    public void onMainEvent(int sss) {
        System.out.println("event = " + sss);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ripple:
                startActivity(new Intent(this, RippleButtonActivity.class));
                break;
            case R.id.btn_advertisement:
                startActivity(new Intent(this, AdvertisementImageActivityKt.class));
                break;
            case R.id.btn_number_rain:
                startActivity(new Intent(this, NumberRainActivity.class));
                break;
        }
    }
}
